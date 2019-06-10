package de.iskae.data.repository.remote

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleRemote
import de.iskae.data.repository.remote.api.NewsApi
import de.iskae.data.repository.remote.mapper.ArticleResponseModelMapper
import io.reactivex.Observable
import javax.inject.Inject

class ArticleRemoteImpl @Inject constructor(private val newsApi: NewsApi,
                                            private val articleResponseModelMapper: ArticleResponseModelMapper) : ArticleRemote {

  override fun getTopHeadlines(country: Country?, category: Category?, page: Int): Observable<List<ArticleEntity>> {
    return newsApi.getTopHeadlines(country?.name, category?.name, page = page)
        .map { topHeadlinesResponseModel ->
          topHeadlinesResponseModel.articles.map { articleResponseModel ->
            articleResponseModelMapper.mapFromModel(articleResponseModel)
          }
        }
  }

}