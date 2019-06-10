package de.iskae.data.repository.remote.mapper

import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.remote.model.ArticleResponseModel
import de.iskae.domain.model.ArticleIdentifier
import javax.inject.Inject

class ArticleResponseModelMapper @Inject constructor(
    private val topHeadlinesPreferencesManager: TopHeadlinesPreferencesManager
) : ModelMapper<ArticleResponseModel, ArticleEntity> {
  override fun mapFromModel(model: ArticleResponseModel): ArticleEntity {
    return ArticleEntity(
        articleIdentifier = ArticleIdentifier(topHeadlinesPreferencesManager.getCountryPreference(),
            topHeadlinesPreferencesManager.getCategoryPreference(),
            topHeadlinesPreferencesManager.getLastRequestedPageNumber()),
        source = model.source.name,
        author = model.author,
        title = model.title,
        description = model.description,
        directUrl = model.url,
        imageUrl = model.urlToImage,
        publishedTime = model.publishedAt,
        content = model.content
    )
  }

}