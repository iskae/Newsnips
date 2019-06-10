package de.iskae.domain.interactor.topheadlines

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.factory.ArticleFactory.makeArticleIdentifier
import de.iskae.domain.factory.ArticleFactory.makeArticleList
import de.iskae.domain.interactor.topheadlines.GetTopHeadlines.Params.Companion.forCountryAndCategory
import de.iskae.domain.model.ArticleIdentifier
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
    val articleIdentifier = ArticleIdentifier(Country.DE.name, null, 0)
    val articles = makeArticleList(5, articleIdentifier)
    whenever(articleRepository.getTopHeadlines(any(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver = getTopHeadlines.buildUseCaseObservable(forCountryAndCategory(false, articleIdentifier))
        .test()

    verify(articleRepository).getTopHeadlines(false, articleIdentifier)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test
  fun getTopHeadlinesForCategorySuccess() {
    val articleIdentifier = ArticleIdentifier(countryCode = null, category = Category.BUSINESS.name, page = 0)
    val articles = makeArticleList(5, articleIdentifier)
    whenever(articleRepository.getTopHeadlines(any(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver = getTopHeadlines.buildUseCaseObservable(forCountryAndCategory(false, articleIdentifier))
        .test()

    verify(articleRepository).getTopHeadlines(false, articleIdentifier)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test
  fun getTopHeadlinesForCountryAndCategorySuccess() {
    val articleIdentifier = makeArticleIdentifier()
    val articles = makeArticleList(5, articleIdentifier)

    whenever(articleRepository.getTopHeadlines(any(), any()))
        .thenReturn(Observable.just(articles))
    val testObserver =
        getTopHeadlines.buildUseCaseObservable(forCountryAndCategory(false, articleIdentifier))
            .test()

    verify(articleRepository).getTopHeadlines(false, articleIdentifier)

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
    getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params(true, ArticleIdentifier(null, null, 0))).test()
  }

}