package de.iskae.data.mapper

import de.iskae.data.model.ArticleEntity
import de.iskae.domain.model.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() : EntityMapper<ArticleEntity, Article> {
  override fun mapToEntity(domain: Article): ArticleEntity {
    return ArticleEntity(
        domain.country,
        domain.category,
        domain.source,
        domain.author,
        domain.title,
        domain.description,
        domain.directUrl,
        domain.imageUrl,
        domain.publishedTime,
        domain.content)
  }

  override fun mapFromEntity(entity: ArticleEntity): Article {
    return Article(
        entity.countryCode,
        entity.category,
        entity.source,
        entity.author,
        entity.title,
        entity.description,
        entity.directUrl,
        entity.imageUrl,
        entity.publishedTime,
        entity.content)
  }

}