package de.iskae.data.remote

import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.factory.ArticleFactory.makeTopHeadlinesResponseModel
import de.iskae.data.repository.remote.ArticleRemoteImpl
import de.iskae.data.repository.remote.api.NewsApi
import de.iskae.data.repository.remote.mapper.ArticleResponseModelMapper
import de.iskae.data.repository.remote.model.TopHeadlinesResponseModel
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ArticleRemoteImplTest {

  private val newsApi = mock<NewsApi>()
  private val mapper = mock<ArticleResponseModelMapper>()
  private val articleRemoteImpl = ArticleRemoteImpl(newsApi, mapper)

  @Test
  fun getArticlesSuccessful() {
    val topHeadlinesResponseModel = makeTopHeadlinesResponseModel(5)
    stubNewsApi(Observable.just(topHeadlinesResponseModel))

    val testObserver = articleRemoteImpl.getTopHeadlines(Country.DE, Category.BUSINESS).test()

    verify(newsApi).getTopHeadlines(Country.DE.name, Category.BUSINESS.name)
    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)
    testObserver.assertValue(topHeadlinesResponseModel.articles.map { articleResponseModel ->
      mapper.mapFromModel(articleResponseModel)
    })
  }

  private fun stubNewsApi(observable: Observable<TopHeadlinesResponseModel>) {
    whenever(newsApi.getTopHeadlines(anyOrNull(), anyOrNull()))
        .thenReturn(observable)
  }

}