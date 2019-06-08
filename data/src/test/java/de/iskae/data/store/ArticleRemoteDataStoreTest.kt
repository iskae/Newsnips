package de.iskae.data.store

import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.factory.ArticleFactory.makeArticleEntityList
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleRemote
import io.reactivex.Observable
import org.junit.Test

class ArticleRemoteDataStoreTest {

  private val articleRemote = mock<ArticleRemote>()
  private val remoteDataStore = ArticleRemoteDataStore(articleRemote)

  @Test
  fun getArticlesSuccessful() {
    val articleEntityList = makeArticleEntityList(5)
    stubRemoteGetTopHeadlines(Observable.just(articleEntityList))

    val testObserver = remoteDataStore.getTopHeadlines(Country.DE, Category.BUSINESS).test()
    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articleEntityList)
  }

  @Test(expected = UnsupportedOperationException::class)
  fun saveTopHeadlinesThrowsException() {
    remoteDataStore.saveTopHeadlines(Country.DE, Category.BUSINESS, listOf()).test()
  }

  @Test(expected = UnsupportedOperationException::class)
  fun clearTopHeadlinesThrowsException() {
    remoteDataStore.clearTopHeadlines(Country.DE, Category.BUSINESS).test()
  }

  private fun stubRemoteGetTopHeadlines(observable: Observable<List<ArticleEntity>>) {
    whenever(remoteDataStore.getTopHeadlines(anyOrNull(), anyOrNull()))
        .thenReturn(observable)
  }
}