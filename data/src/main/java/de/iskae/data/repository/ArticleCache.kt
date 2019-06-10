package de.iskae.data.repository

import de.iskae.data.model.ArticleEntity
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ArticleCache {
  fun clearTopHeadlines(articleIdentifier: ArticleIdentifier): Completable
  fun saveTopHeadlines(articles: List<ArticleEntity>): Completable
  fun getTopHeadlines(articleIdentifier: ArticleIdentifier): Observable<List<ArticleEntity>>
  fun isTopHeadlinesCached(articleIdentifier: ArticleIdentifier): Single<Boolean>
  fun setLastCacheTime(articleIdentifier: ArticleIdentifier, lastCacheTime: Long): Completable
  fun isTopHeadlinesCacheExpired(articleIdentifier: ArticleIdentifier): Single<Boolean>
}