package de.iskae.data.repository.cache.mapper

import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.cache.model.CachedArticle
import javax.inject.Inject

class CachedArticleMapper @Inject constructor() : CacheMapper<CachedArticle, ArticleEntity> {
  override fun mapToEntity(cache: CachedArticle): ArticleEntity {
    return ArticleEntity(
        articleIdentifier = cache.articleIdentifier,
        source = cache.source,
        author = cache.author,
        title = cache.title,
        description = cache.description,
        directUrl = cache.directUrl,
        imageUrl = cache.imageUrl,
        publishedTime = cache.publishedTime,
        content = cache.content
    )
  }

  override fun mapFromEntity(entity: ArticleEntity): CachedArticle {
    return CachedArticle(
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