package de.iskae.data.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.factory.ArticleFactory.makeCachedArticleList
import de.iskae.data.repository.cache.db.ArticleDatabase
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedArticleDaoTest {

  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val database = Room.inMemoryDatabaseBuilder(
      RuntimeEnvironment.application.applicationContext,
      ArticleDatabase::class.java)
      .allowMainThreadQueries()
      .build()

  @After
  fun closeDb() {
    database.close()
  }

  @Test
  fun getArticlesSuccessful() {
    val articles = makeCachedArticleList(10)
    database.cachedArticleDao().insertArticles(articles)

    val testObserver = database.cachedArticleDao().getArticles(Country.DE.name, Category.BUSINESS.name).test()
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test
  fun clearArticlesSuccessful() {
    val articles = makeCachedArticleList(10)
    database.cachedArticleDao().insertArticles(articles)

    val testObserver = database.cachedArticleDao().getArticles(Country.DE.name, Category.BUSINESS.name).test()
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)

    database.cachedArticleDao().deleteArticles(Country.DE.name, Category.BUSINESS.name)

    val emptyDbTestObserver = database.cachedArticleDao().getArticles(Country.DE.name, Category.BUSINESS.name).test()
    emptyDbTestObserver.assertNoErrors()
    emptyDbTestObserver.assertValueCount(1)
    emptyDbTestObserver.assertValue(emptyList())
  }

  @Test
  fun deleteAllArticlesSuccessful(){
    val articles = makeCachedArticleList(10)
    database.cachedArticleDao().insertArticles(articles)

    val testObserver = database.cachedArticleDao().getArticles(Country.DE.name, Category.BUSINESS.name).test()
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)

    database.cachedArticleDao().deleteAllArticles()

    val emptyDbTestObserver = database.cachedArticleDao().getArticles(Country.DE.name, Category.BUSINESS.name).test()
    emptyDbTestObserver.assertNoErrors()
    emptyDbTestObserver.assertValueCount(1)
    emptyDbTestObserver.assertValue(emptyList())
  }
}