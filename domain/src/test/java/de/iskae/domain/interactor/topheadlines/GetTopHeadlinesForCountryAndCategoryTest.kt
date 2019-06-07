package de.iskae.domain.interactor.topheadlines

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.factory.ArticleFactory
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetTopHeadlinesForCountryAndCategoryTest {
  private lateinit var getTopHeadlinesForCountryAndCategory: GetTopHeadlinesForCountryAndCategory
  @Mock lateinit var articleRepository: ArticleRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    getTopHeadlinesForCountryAndCategory = GetTopHeadlinesForCountryAndCategory(articleRepository, postExecutionThread)
  }

  @Test
  fun getTopHeadlinesForCountryAndCategorySuccess() {
    val articles = ArticleFactory.makeArticleList(5)
    whenever(articleRepository.getTopHeadlinesForCountryAndCategory(any(), any(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver = getTopHeadlinesForCountryAndCategory
        .buildUseCaseObservable(GetTopHeadlinesForCountryAndCategory.Params.forCountryAndCategory(false, Country.DE, Category.BUSINESS))
        .test()

    verify(articleRepository).getTopHeadlinesForCountryAndCategory(false, Country.DE.name, Category.BUSINESS.name)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test(expected = IllegalArgumentException::class)
  fun getTopHeadlinesForCountryAndCategoryFailsWhenParamsMissing() {
    getTopHeadlinesForCountryAndCategory.buildUseCaseObservable().test()
  }

}