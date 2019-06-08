package de.iskae.data.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.factory.ArticleFactory.makeArticleEntityList
import de.iskae.data.repository.cache.ArticleCacheImpl
import de.iskae.data.repository.cache.db.ArticleDatabase
import de.iskae.data.repository.cache.mapper.CachedArticleMapper
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ArticleCacheImplTest {

  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application.applicationContext,
      ArticleDatabase::class.java)
      .allowMainThreadQueries()
      .build()

  private val mapper = CachedArticleMapper()
  private val articleCacheImpl = ArticleCacheImpl(database, mapper)

  @After
  fun closeDb() {
    database.close()
  }

  @Test
  fun clearArticlesCompletes() {
    val countryTestObserver = articleCacheImpl.clearTopHeadlines(Country.DE, null).test()
    countryTestObserver.assertComplete()
    val categoryTestObserver = articleCacheImpl.clearTopHeadlines(null, Category.BUSINESS).test()
    categoryTestObserver.assertComplete()
    val countryAndCategoryTestObserver = articleCacheImpl.clearTopHeadlines(Country.DE, Category.BUSINESS).test()
    countryAndCategoryTestObserver.assertComplete()
  }

  @Test
  fun saveArticlesSuccessful() {
    val articles = makeArticleEntityList(10)

    val testObserver = articleCacheImpl.saveTopHeadlines(articles).test()
    testObserver.assertComplete()

    //The mock data always has DE and BUSINESS
    val getCachedArticlesObserver = articleCacheImpl.getTopHeadlines(Country.DE, Category.BUSINESS).test()
    getCachedArticlesObserver.assertNoErrors()
    getCachedArticlesObserver.assertValueCount(1)
    getCachedArticlesObserver.assertValue(articles)

    val getNonExistentCachedObservable = articleCacheImpl.getTopHeadlines(null, null).test()
    getNonExistentCachedObservable.assertNoErrors()
    getNonExistentCachedObservable.assertValueCount(1)
    getNonExistentCachedObservable.assertValue(listOf())
  }

  @Test
  fun isTopHeadlinesCachedReturnsData() {
    val nonExistentCacheObserver = articleCacheImpl.isTopHeadlinesCached(Country.DE, Category.BUSINESS).test()
    nonExistentCacheObserver.assertValue(false)

    val articles = makeArticleEntityList(10)
    articleCacheImpl.saveTopHeadlines(articles).test()

    val testObserver = articleCacheImpl.isTopHeadlinesCached(Country.DE, Category.BUSINESS).test()
    testObserver.assertValue(true)
  }

}