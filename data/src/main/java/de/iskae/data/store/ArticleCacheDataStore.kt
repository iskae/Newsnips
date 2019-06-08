package de.iskae.data.store

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleCache
import de.iskae.data.repository.ArticleDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ArticleCacheDataStore @Inject constructor(private val articleCache: ArticleCache) : ArticleDataStore {
  override fun getTopHeadlines(country: Country?, category: Category?): Observable<List<ArticleEntity>> {
    return articleCache.getTopHeadlines(country, category)
  }

  override fun saveTopHeadlines(country: Country?, category: Category?, articles: List<ArticleEntity>): Completable {
    return articleCache.saveTopHeadlines(articles)
        .andThen(articleCache.setLastCacheTime(country, category, System.currentTimeMillis()))
  }

  override fun clearTopHeadlines(country: Country?, category: Category?): Completable {
    return articleCache.clearTopHeadlines(country, category)
  }

}