package de.iskae.data.repository

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.model.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ArticleCache {
  fun clearTopHeadlines(country: Country?, category: Category?): Completable
  fun saveTopHeadlines(articles: List<ArticleEntity>): Completable
  fun getTopHeadlines(country: Country?, category: Category?): Observable<List<ArticleEntity>>
  fun isTopHeadlinesCached(country: Country?, category: Category?): Single<Boolean>
  fun setLastCacheTime(country: Country?, category: Category?, lastCacheTime: Long): Completable
  fun isTopHeadlinesCacheExpired(country: Country?, category: Category?): Single<Boolean>
}