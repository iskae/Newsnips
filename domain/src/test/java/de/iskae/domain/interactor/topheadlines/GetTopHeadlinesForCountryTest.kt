package de.iskae.domain.interactor.topheadlines

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Country
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.factory.ArticleFactory.makeArticleList
import de.iskae.domain.interactor.topheadlines.GetTopHeadlinesForCountry.Params.Companion.forCountry
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetTopHeadlinesForCountryTest {
  private lateinit var getTopHeadlinesForCountry: GetTopHeadlinesForCountry
  @Mock lateinit var articleRepository: ArticleRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    getTopHeadlinesForCountry = GetTopHeadlinesForCountry(articleRepository, postExecutionThread)
  }

  @Test
  fun getTopHeadlinesForCountrySuccess() {
    val articles = makeArticleList(5)
    whenever(articleRepository.getTopHeadlinesForCountry(any(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver = getTopHeadlinesForCountry.buildUseCaseObservable(forCountry(false, Country.DE))
        .test()

    verify(articleRepository).getTopHeadlinesForCountry(false, Country.DE.name)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test(expected = IllegalArgumentException::class)
  fun getTopHeadlinesForCountryFailsWhenParamsMissing() {
    getTopHeadlinesForCountry.buildUseCaseObservable().test()
  }

}