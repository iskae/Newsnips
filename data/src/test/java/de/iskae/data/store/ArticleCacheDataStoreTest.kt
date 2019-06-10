package de.iskae.data.store

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.data.factory.ArticleFactory.makeArticleEntityList
import de.iskae.data.factory.ArticleFactory.makeArticleIdentifier
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleCache
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test

class ArticleCacheDataStoreTest {

  private val articleCache = mock<ArticleCache>()
  private val articleCacheDataStore = ArticleCacheDataStore(articleCache)
  private val articleIdentifier = makeArticleIdentifier()
  private val articleEntityList = makeArticleEntityList(5, articleIdentifier)

  @Test
  fun getArticlesSuccessful() {
    stubCacheGetTopHeadlines(Observable.just(articleEntityList))

    val testObserver = articleCacheDataStore.getTopHeadlines(articleIdentifier).test()
    verify(articleCache).getTopHeadlines(articleIdentifier)
    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articleEntityList)
  }

  //Disabled because of weird mockito issues...
//  @Test
//  fun saveTopHeadlinesSuccessful() {
//    stubSaveTopHeadlines(Completable.complete())
//    val testObserver = articleCacheDataStore.saveTopHeadlines(articleIdentifier, articleEntityList).test()
//    testObserver.assertComplete()
//  }

  @Test
  fun clearTopHeadlinesSuccessful() {
    stubArticleCacheClearTopHeadlines(Completable.complete())
    val testObserver = articleCacheDataStore.clearTopHeadlines(articleIdentifier).test()
    verify(articleCache).clearTopHeadlines(articleIdentifier)
    testObserver.assertComplete()
  }

  private fun stubCacheGetTopHeadlines(observable: Observable<List<ArticleEntity>>) {
    whenever(articleCacheDataStore.getTopHeadlines(articleIdentifier))
        .thenReturn(observable)
  }

  private fun stubSaveTopHeadlines(completable: Completable) {
    stubArticleCacheSetLastCacheTime(completable)
    whenever(articleCacheDataStore.saveTopHeadlines(articleIdentifier, articleEntityList))
        .thenReturn(completable)
  }

  private fun stubArticleCacheSetLastCacheTime(completable: Completable) {
    whenever(articleCache.setLastCacheTime(articleIdentifier, 0L))
        .thenReturn(completable)
  }

  private fun stubArticleCacheClearTopHeadlines(completable: Completable) {
    whenever(articleCache.clearTopHeadlines(articleIdentifier))
        .thenReturn(completable)
  }
}