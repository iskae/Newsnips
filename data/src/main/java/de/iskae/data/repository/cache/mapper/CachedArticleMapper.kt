package de.iskae.data.repository.cache.mapper

import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.cache.model.CachedArticle
import javax.inject.Inject

class CachedArticleMapper @Inject constructor() : CacheMapper<CachedArticle, ArticleEntity> {
  override fun mapToEntity(cache: CachedArticle): ArticleEntity {
    return ArticleEntity(
        cache.countryCode,
        cache.category,
        cache.source,
        cache.author,
        cache.title,
        cache.description,
        cache.directUrl,
        cache.imageUrl,
        cache.publishedTime,
        cache.content
    )
  }

  override fun mapFromEntity(entity: ArticleEntity): CachedArticle {
    return CachedArticle(
        entity.countryCode,
        entity.category,
        entity.source,
        entity.author,
        entity.title,
        entity.description,
        entity.directUrl,
        entity.imageUrl,
        entity.publishedTime,
        entity.content
    )
  }

}