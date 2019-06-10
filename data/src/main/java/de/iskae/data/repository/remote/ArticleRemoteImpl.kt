package de.iskae.data.repository.remote

import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleRemote
import de.iskae.data.repository.remote.api.NewsApi
import de.iskae.data.repository.remote.mapper.ArticleResponseModelMapper
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Observable
import javax.inject.Inject

class ArticleRemoteImpl @Inject constructor(private val newsApi: NewsApi,
                                            private val articleResponseModelMapper: ArticleResponseModelMapper) : ArticleRemote {

  override fun getTopHeadlines(articleIdentifier: ArticleIdentifier): Observable<List<ArticleEntity>> {
    return newsApi.getTopHeadlines(countryCode = articleIdentifier.countryCode,
        category = articleIdentifier.category,
        page = articleIdentifier.page)
        .map { topHeadlinesResponseModel ->
          topHeadlinesResponseModel.articles.map { articleResponseModel ->
            articleResponseModelMapper.mapFromModel(articleResponseModel)
          }
        }
  }

}