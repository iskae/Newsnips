package de.iskae.domain.interactor.topheadlines

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Category
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.factory.ArticleFactory
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetTopHeadlinesForCategoryTest {
  private lateinit var getTopHeadlinesForCategory: GetTopHeadlinesForCategory
  @Mock lateinit var articleRepository: ArticleRepository
  @Mock lateinit var postExecutionThread: PostExecutionThread

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    getTopHeadlinesForCategory = GetTopHeadlinesForCategory(articleRepository, postExecutionThread)
  }

  @Test
  fun getTopHeadlinesForCategorySuccess() {
    val articles = ArticleFactory.makeArticleList(5)
    whenever(articleRepository.getTopHeadlinesForCategory(any(), any()))
        .thenReturn(Observable.just(articles))

    val testObserver = getTopHeadlinesForCategory.buildUseCaseObservable(GetTopHeadlinesForCategory.Params.forCategory(false, Category.BUSINESS))
        .test()

    verify(articleRepository).getTopHeadlinesForCategory(false, Category.BUSINESS.name)

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(articles)
  }

  @Test(expected = IllegalArgumentException::class)
  fun getTopHeadlinesForCategoryFailsWhenParamsMissing() {
    getTopHeadlinesForCategory.buildUseCaseObservable().test()
  }

}