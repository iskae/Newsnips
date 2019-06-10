package de.iskae.data.mapper

import de.iskae.data.model.ArticleEntity
import de.iskae.domain.model.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() : EntityMapper<ArticleEntity, Article> {
  override fun mapToEntity(domain: Article): ArticleEntity {
    return ArticleEntity(
        articleIdentifier = domain.articleIdentifier,
        source = domain.source,
        author = domain.author,
        title = domain.title,
        description = domain.description,
        directUrl = domain.directUrl,
        imageUrl = domain.imageUrl,
        publishedTime = domain.publishedTime,
        content = domain.content)
  }

  override fun mapFromEntity(entity: ArticleEntity): Article {
    return Article(
        articleIdentifier = entity.articleIdentifier,
        source = entity.source,
        author = entity.author,
        title = entity.title,
        description = entity.description,
        directUrl = entity.directUrl,
        imageUrl = entity.imageUrl,
        publishedTime = entity.publishedTime,
        content = entity.content
    )
  }

}