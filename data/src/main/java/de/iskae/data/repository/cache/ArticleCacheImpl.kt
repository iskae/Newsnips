package de.iskae.data.repository.cache

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleCache
import de.iskae.data.repository.cache.db.ArticleDatabase
import de.iskae.data.repository.cache.mapper.CachedArticleMapper
import de.iskae.data.repository.cache.model.Config
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArticleCacheImpl @Inject constructor(
    private val articleDatabase: ArticleDatabase,
    private val cachedArticleMapper: CachedArticleMapper
) : ArticleCache {
  override fun clearTopHeadlines(country: Country?, category: Category?): Completable {
    return Completable.defer {
      when {
        country != null && category != null -> articleDatabase.cachedArticleDao().deleteArticlesByCountryAndCategory(country.name, category.name)
        country != null -> articleDatabase.cachedArticleDao().deleteArticlesByCountry(country.name)
        category != null -> articleDatabase.cachedArticleDao().deleteArticlesByCategory(category.name)
      }
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

  override fun getTopHeadlines(country: Country?, category: Category?): Observable<List<ArticleEntity>> {
    val articleList = when {
      country != null && category != null -> articleDatabase.cachedArticleDao().getArticlesByCountryAndCategory(country.name, category.name)
      else -> articleDatabase.cachedArticleDao().getArticlesByCountryOrCategory(country?.name, category?.name)
    }
    return articleList
        .toObservable()
        .map { cachedArticles ->
          cachedArticles.map { cachedArticle ->
            cachedArticleMapper.mapToEntity(cachedArticle)
          }
        }
  }

  override fun isTopHeadlinesCached(country: Country?, category: Category?): Single<Boolean> {
    val articleList = when {
      country != null && category != null -> articleDatabase.cachedArticleDao().getArticlesByCountryAndCategory(country.name, category.name)
      else -> articleDatabase.cachedArticleDao().getArticlesByCountryOrCategory(country?.name, category?.name)
    }
    return articleList
        .onErrorReturn { listOf() }
        .firstOrError()
        .map { cachedArticles -> cachedArticles.isNotEmpty() }
  }

  override fun setLastCacheTime(country: Country?, category: Category?, lastCacheTime: Long): Completable {
    return Completable.defer {
      articleDatabase.configDao()
          .insertConfig(Config(lastCacheTime = lastCacheTime, countryCode = country?.name, category = category?.name))
      Completable.complete()
    }
  }

  override fun isTopHeadlinesCacheExpired(country: Country?, category: Category?): Single<Boolean> {
    val currentTime = System.currentTimeMillis()
    val cacheExpirationTime = TimeUnit.MINUTES.toMillis(15)//New articles show up with a 15 min delay
    val config = when {
      country != null && category != null -> articleDatabase.configDao().getConfigByCountryAndCategory(country.name, category.name)
      else -> articleDatabase.configDao().getConfigByCountryOrCategory(country?.name, category?.name)
    }
    return config
        .defaultIfEmpty(Config(id = 0, lastCacheTime = 0, countryCode = country?.name, category = category?.name))
        .map {
          currentTime - it.lastCacheTime > cacheExpirationTime
        }.toSingle()
  }

}