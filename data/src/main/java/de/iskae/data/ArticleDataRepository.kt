package de.iskae.data

import de.iskae.data.mapper.ArticleMapper
import de.iskae.data.repository.ArticleCache
import de.iskae.data.store.ArticleDataStoreFactory
import de.iskae.domain.model.Article
import de.iskae.domain.model.ArticleIdentifier
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ArticleDataRepository @Inject constructor(private val mapper: ArticleMapper,
                                                private val articleCache: ArticleCache,
                                                private val dataStoreFactory: ArticleDataStoreFactory) : ArticleRepository {

  override fun getTopHeadlines(forceRefresh: Boolean, articleIdentifier: ArticleIdentifier): Observable<List<Article>> {
    return Observable.zip(articleCache.isTopHeadlinesCached(articleIdentifier).toObservable(),
        articleCache.isTopHeadlinesCacheExpired(articleIdentifier).toObservable(),
        BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areCached, isExpired ->
          Pair(areCached, isExpired)
        })
        .flatMap {
          dataStoreFactory.getDataStore(forceRefresh, it.first, it.second)
              .getTopHeadlines(articleIdentifier)
        }
        .flatMap { topHeadlines ->
          dataStoreFactory.getCacheDataStore()
              .saveTopHeadlines(articleIdentifier, topHeadlines)
              .andThen(Observable.just(topHeadlines))
        }
        .map { articles ->
          articles.map { article -> mapper.mapFromEntity(article) }
        }
  }

}