package de.iskae.data.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import de.iskae.data.factory.ArticleFactory.makeArticleEntityList
import de.iskae.data.factory.ArticleFactory.makeArticleIdentifier
import de.iskae.data.repository.cache.ArticleCacheImpl
import de.iskae.data.repository.cache.db.ArticleDatabase
import de.iskae.data.repository.cache.mapper.CachedArticleMapper
import de.iskae.domain.model.ArticleIdentifier
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
    val articleIdentifier = makeArticleIdentifier()
    val countryTestObserver = articleCacheImpl.clearTopHeadlines(articleIdentifier).test()
    countryTestObserver.assertComplete()
    val categoryTestObserver = articleCacheImpl.clearTopHeadlines(articleIdentifier).test()
    categoryTestObserver.assertComplete()
    val countryAndCategoryTestObserver = articleCacheImpl.clearTopHeadlines(articleIdentifier).test()
    countryAndCategoryTestObserver.assertComplete()
  }

  @Test
  fun saveArticlesSuccessful() {
    val articleIdentifier = makeArticleIdentifier()
    val articles = makeArticleEntityList(10, articleIdentifier)

    val testObserver = articleCacheImpl.saveTopHeadlines(articles).test()
    testObserver.assertComplete()

    //The mock data always has DE and BUSINESS
    val getCachedArticlesObserver = articleCacheImpl.getTopHeadlines(articleIdentifier).test()
    getCachedArticlesObserver.assertNoErrors()
    getCachedArticlesObserver.assertValueCount(1)
    getCachedArticlesObserver.assertValue(articles)

    val getNonExistentCachedObservable = articleCacheImpl.getTopHeadlines(ArticleIdentifier(null, null, 0)).test()
    getNonExistentCachedObservable.assertNoErrors()
    getNonExistentCachedObservable.assertValueCount(1)
    getNonExistentCachedObservable.assertValue(listOf())
  }

  @Test
  fun isTopHeadlinesCachedReturnsData() {
    val articleIdentifier = makeArticleIdentifier()
    val nonExistentCacheObserver = articleCacheImpl.isTopHeadlinesCached(articleIdentifier).test()
    nonExistentCacheObserver.assertValue(false)

    val articles = makeArticleEntityList(10, articleIdentifier)
    articleCacheImpl.saveTopHeadlines(articles).test()

    val testObserver = articleCacheImpl.isTopHeadlinesCached(articleIdentifier).test()
    testObserver.assertValue(true)
  }

}