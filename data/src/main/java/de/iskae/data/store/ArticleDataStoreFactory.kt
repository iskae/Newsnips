package de.iskae.data.store

import de.iskae.data.repository.ArticleDataStore
import javax.inject.Inject

class ArticleDataStoreFactory @Inject constructor(private val cacheDataStore: ArticleCacheDataStore,
                                                  private val remoteDataStore: ArticleRemoteDataStore) {

  fun getDataStore(forceRefresh: Boolean, isArticlesCached: Boolean, isCacheExpired: Boolean): ArticleDataStore {
    return if (!forceRefresh && isArticlesCached && !isCacheExpired) {
      cacheDataStore
    } else {
      remoteDataStore
    }
  }

  fun getCacheDataStore(): ArticleDataStore {
    return cacheDataStore
  }
}