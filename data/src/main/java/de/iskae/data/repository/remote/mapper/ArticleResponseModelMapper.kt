package de.iskae.data.repository.remote.mapper

import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.remote.model.ArticleResponseModel
import javax.inject.Inject

class ArticleResponseModelMapper @Inject constructor(
    private val topHeadlinesPreferencesManager: TopHeadlinesPreferencesManager
) : ModelMapper<ArticleResponseModel, ArticleEntity> {
  override fun mapFromModel(model: ArticleResponseModel): ArticleEntity {
    return ArticleEntity(
        topHeadlinesPreferencesManager.getCountryPreference(),
        topHeadlinesPreferencesManager.getCategoryPreference(),
        model.sourceResponseModel.name,
        model.author,
        model.title,
        model.description,
        model.url,
        model.urlToImage,
        model.publishedAt,
        model.content
    )
  }

}