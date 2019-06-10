package de.iskae.data.store

import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleCache
import de.iskae.data.repository.ArticleDataStore
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ArticleCacheDataStore @Inject constructor(private val articleCache: ArticleCache) : ArticleDataStore {
  override fun getTopHeadlines(articleIdentifier: ArticleIdentifier): Observable<List<ArticleEntity>> {
    return articleCache.getTopHeadlines(articleIdentifier)
  }

  override fun saveTopHeadlines(articleIdentifier: ArticleIdentifier, articles: List<ArticleEntity>): Completable {
    return articleCache.saveTopHeadlines(articles)
        .andThen(articleCache.setLastCacheTime(articleIdentifier, System.currentTimeMillis()))
  }

  override fun clearTopHeadlines(articleIdentifier: ArticleIdentifier): Completable {
    return articleCache.clearTopHeadlines(articleIdentifier)
  }

}