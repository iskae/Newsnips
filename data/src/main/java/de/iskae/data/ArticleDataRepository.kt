package de.iskae.data

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.mapper.ArticleMapper
import de.iskae.data.repository.ArticleCache
import de.iskae.data.store.ArticleDataStoreFactory
import de.iskae.domain.model.Article
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ArticleDataRepository @Inject constructor(private val mapper: ArticleMapper,
                                                private val articleCache: ArticleCache,
                                                private val dataStoreFactory: ArticleDataStoreFactory) : ArticleRepository {

  override fun getTopHeadlines(forceRefresh: Boolean, countryCode: String?, category: String?): Observable<List<Article>> {
    val country = countryCode?.let { Country.valueOf(it) }
    val category = category?.let { Category.valueOf(it) }
    return Observable.zip(articleCache.isTopHeadlinesCached(country, category).toObservable(),
        articleCache.isTopHeadlinesCacheExpired(country, category).toObservable(),
        BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areCached, isExpired ->
          Pair(areCached, isExpired)
        })
        .flatMap {
          dataStoreFactory.getDataStore(forceRefresh, it.first, it.second)
              .getTopHeadlines(country, category)
        }
        .flatMap { topHeadlines ->
          dataStoreFactory.getCacheDataStore()
              .saveTopHeadlines(country, category, topHeadlines)
              .andThen(Observable.just(topHeadlines))
        }
        .map { articleEntities ->
          articleEntities.map { articleEntity ->
            mapper.mapFromEntity(articleEntity)
          }
        }
  }

}