package de.iskae.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.data.factory.ArticleFactory.makeArticleEntityList
import de.iskae.data.factory.ArticleFactory.makeArticleIdentifier
import de.iskae.data.mapper.ArticleMapper
import de.iskae.data.repository.ArticleCache
import de.iskae.data.repository.ArticleDataStore
import de.iskae.data.store.ArticleDataStoreFactory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ArticleDataRepositoryTest {

  private val mapper = ArticleMapper()
  private val cache = mock<ArticleCache>()
  private val store = mock<ArticleDataStore>()
  private val dataStoreFactory = mock<ArticleDataStoreFactory>()
  private val articleDataRepository = ArticleDataRepository(mapper, cache, dataStoreFactory)

  @Test
  fun getTopHeadlinesSuccessful() {
    val articleIdentifier = makeArticleIdentifier()
    val articleEntityList = makeArticleEntityList(5, articleIdentifier)

    whenever(dataStoreFactory.getCacheDataStore())
        .thenReturn(store)
    whenever(dataStoreFactory.getDataStore(any(), any(), any()))
        .thenReturn(store)
    whenever(store.getTopHeadlines(any()))
        .thenReturn(Observable.just(articleEntityList))
    whenever(store.saveTopHeadlines(any(), any()))
        .thenReturn(Completable.complete())
    whenever(cache.isTopHeadlinesCached(any()))
        .thenReturn(Single.just(false))
    whenever(cache.isTopHeadlinesCacheExpired(any()))
        .thenReturn(Single.just(false))
    whenever(cache.saveTopHeadlines(any()))
        .thenReturn(Completable.complete())

    val testObserver = articleDataRepository.getTopHeadlines(false, articleIdentifier).test()
    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articleEntityList.map { articleEntity ->
      mapper.mapFromEntity(articleEntity)
    })
  }
}