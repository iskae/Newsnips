package de.iskae.data.store

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.data.factory.ArticleFactory.makeArticleEntityList
import de.iskae.data.factory.ArticleFactory.makeArticleIdentifier
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleRemote
import io.reactivex.Observable
import org.junit.Test

class ArticleRemoteDataStoreTest {

  private val articleRemote = mock<ArticleRemote>()
  private val remoteDataStore = ArticleRemoteDataStore(articleRemote)
  private val articleIdentifier = makeArticleIdentifier()
  private val articleEntityList = makeArticleEntityList(5, articleIdentifier)

  @Test
  fun getArticlesSuccessful() {
    stubRemoteGetTopHeadlines(Observable.just(articleEntityList))

    val testObserver = remoteDataStore.getTopHeadlines(articleIdentifier).test()
    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articleEntityList)
  }

  @Test(expected = UnsupportedOperationException::class)
  fun clearTopHeadlinesThrowsException() {
    remoteDataStore.clearTopHeadlines(makeArticleIdentifier()).test()
  }

  @Test(expected = UnsupportedOperationException::class)
  fun saveTopHeadlinesThrowsException() {
    remoteDataStore.saveTopHeadlines(makeArticleIdentifier(), listOf()).test()
  }

  private fun stubRemoteGetTopHeadlines(observable: Observable<List<ArticleEntity>>) {
    whenever(remoteDataStore.getTopHeadlines(articleIdentifier))
        .thenReturn(observable)
  }
}