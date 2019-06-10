package de.iskae.domain.interactor.topheadlines

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.factory.ArticleFactory.makeArticleList
import de.iskae.domain.interactor.topheadlines.GetTopHeadlines.Params.Companion.forCountryAndCategory
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetTopHeadlinesTest {
  private lateinit var getTopHeadlines: GetTopHeadlines
  @Mock lateinit var articleRepository: ArticleRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    getTopHeadlines = GetTopHeadlines(articleRepository, postExecutionThread)
  }

  @Test
  fun getTopHeadlinesForCountrySuccess() {
    val articles = makeArticleList(5)
    whenever(articleRepository.getTopHeadlines(any(), anyOrNull(), anyOrNull(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver = getTopHeadlines.buildUseCaseObservable(forCountryAndCategory(false, Country.DE, null, 0))
        .test()

    verify(articleRepository).getTopHeadlines(false, Country.DE.name, null, 0)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test
  fun getTopHeadlinesForCategorySuccess() {
    val articles = makeArticleList(5)
    whenever(articleRepository.getTopHeadlines(any(), anyOrNull(), anyOrNull(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver = getTopHeadlines.buildUseCaseObservable(forCountryAndCategory(false, null, Category.BUSINESS, 0))
        .test()

    verify(articleRepository).getTopHeadlines(false, null, Category.BUSINESS.name, 0)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test
  fun getTopHeadlinesForCountryAndCategorySuccess() {
    val articles = makeArticleList(5)
    whenever(articleRepository.getTopHeadlines(any(), anyOrNull(), anyOrNull(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver =
        getTopHeadlines.buildUseCaseObservable(forCountryAndCategory(false, Country.DE, Category.BUSINESS, 0))
            .test()

    verify(articleRepository).getTopHeadlines(false, Country.DE.name, Category.BUSINESS.name, 0)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test(expected = IllegalArgumentException::class)
  fun getTopHeadlinesFailsWhenParamsMissing() {
    getTopHeadlines.buildUseCaseObservable().test()
  }

  @Test(expected = IllegalArgumentException::class)
  fun getTopHeadlinesFailsWhenParamsDoesNotIncludeRequiredParameters() {
    getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params(true, null, null, 0)).test()
  }

}