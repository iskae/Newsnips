package de.iskae.data.store

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.factory.ArticleFactory.makeArticleEntityList
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleCache
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt

class ArticleCacheDataStoreTest {

  private val articleCache = mock<ArticleCache>()
  private val articleCacheDataStore = ArticleCacheDataStore(articleCache)

  @Test
  fun getArticlesSuccessful() {
    val articleEntityList = makeArticleEntityList(5)
    stubCacheGetTopHeadlines(Observable.just(articleEntityList))

    val testObserver = articleCacheDataStore.getTopHeadlines(Country.DE, Category.BUSINESS, 0).test()
    verify(articleCache).getTopHeadlines(Country.DE, Category.BUSINESS)
    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articleEntityList)
  }

  @Test
  fun saveTopHeadlinesSuccessful() {
    val articleEntityList = makeArticleEntityList(5)
    stubSaveTopHeadlines(Completable.complete())
    stubArticleCacheSetLastCacheTime(Completable.complete())

    val testObserver = articleCacheDataStore.saveTopHeadlines(Country.DE, Category.BUSINESS, articleEntityList).test()
    verify(articleCache).saveTopHeadlines(articleEntityList)
    testObserver.assertComplete()
  }

  @Test
  fun clearTopHeadlinesSuccessful() {
    stubArticleCacheClearTopHeadlines(Completable.complete())
    val testObserver = articleCacheDataStore.clearTopHeadlines(Country.DE, Category.BUSINESS).test()
    verify(articleCache).clearTopHeadlines(Country.DE, Category.BUSINESS)
    testObserver.assertComplete()
  }

  private fun stubCacheGetTopHeadlines(observable: Observable<List<ArticleEntity>>) {
    whenever(articleCacheDataStore.getTopHeadlines(anyOrNull(), anyOrNull(), 0))
        .thenReturn(observable)
  }

  private fun stubSaveTopHeadlines(completable: Completable) {
    stubArticleCacheClearTopHeadlines(completable)
    whenever(articleCache.saveTopHeadlines(any()))
        .thenReturn(completable)
  }

  private fun stubArticleCacheSetLastCacheTime(completable: Completable) {
    whenever(articleCache.setLastCacheTime(anyOrNull(), anyOrNull(), any()))
        .thenReturn(completable)
  }

  private fun stubArticleCacheClearTopHeadlines(completable: Completable) {
    whenever(articleCache.clearTopHeadlines(anyOrNull(), anyOrNull()))
        .thenReturn(completable)
  }
}