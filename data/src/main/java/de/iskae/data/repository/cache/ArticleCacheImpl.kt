package de.iskae.data.repository.cache

import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleCache
import de.iskae.data.repository.cache.db.ArticleDatabase
import de.iskae.data.repository.cache.db.ArticleDbConstants.CACHE_EXPIRATION_MINUTES
import de.iskae.data.repository.cache.mapper.CachedArticleMapper
import de.iskae.data.repository.cache.model.Config
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArticleCacheImpl @Inject constructor(
    private val articleDatabase: ArticleDatabase,
    private val cachedArticleMapper: CachedArticleMapper
) : ArticleCache {
  override fun clearTopHeadlines(articleIdentifier: ArticleIdentifier): Completable {
    return Completable.defer {
      articleDatabase.cachedArticleDao().deleteArticles(articleIdentifier)
      Completable.complete()
    }
  }

  override fun saveTopHeadlines(articles: List<ArticleEntity>): Completable {
    return Completable.defer {
      articleDatabase.cachedArticleDao()
          .insertArticles(articles.map { articleEntity -> cachedArticleMapper.mapFromEntity(articleEntity) })
      Completable.complete()
    }
  }

  override fun getTopHeadlines(articleIdentifier: ArticleIdentifier): Observable<List<ArticleEntity>> {
    return articleDatabase.cachedArticleDao().getArticles(articleIdentifier)
        .toObservable()
        .map { cachedArticles ->
          cachedArticles.map { cachedArticle ->
            cachedArticleMapper.mapToEntity(cachedArticle)
          }
        }
  }

  override fun isTopHeadlinesCached(articleIdentifier: ArticleIdentifier): Single<Boolean> {
    return articleDatabase.cachedArticleDao().getArticles(articleIdentifier)
        .onErrorReturn { listOf() }
        .firstOrError()
        .map { cachedArticles -> cachedArticles.isNotEmpty() }
  }

  override fun setLastCacheTime(articleIdentifier: ArticleIdentifier, lastCacheTime: Long): Completable {
    return Completable.defer {
      articleDatabase.configDao()
          .insertConfig(Config(lastCacheTime = lastCacheTime, articleIdentifier = articleIdentifier))
      Completable.complete()
    }
  }

  override fun isTopHeadlinesCacheExpired(articleIdentifier: ArticleIdentifier): Single<Boolean> {
    val currentTime = System.currentTimeMillis()
    val cacheExpirationTime = TimeUnit.MINUTES.toMillis(CACHE_EXPIRATION_MINUTES)//New articles show up with a 15 min delay
    return articleDatabase.configDao().getConfig(articleIdentifier)
        .defaultIfEmpty(Config(id = 0, lastCacheTime = 0, articleIdentifier = ArticleIdentifier(null, null, 0)))
        .map {
          currentTime - it.lastCacheTime > cacheExpirationTime
        }.toSingle()
  }

}