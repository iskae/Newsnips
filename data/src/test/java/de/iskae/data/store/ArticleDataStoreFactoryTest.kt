package de.iskae.data.store

import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ArticleDataStoreFactoryTest {

  private val articleCacheDataStore = mock<ArticleCacheDataStore>()
  private val articleRemoteDataStore = mock<ArticleRemoteDataStore>()
  private val articleDataStoreFactory = ArticleDataStoreFactory(articleCacheDataStore, articleRemoteDataStore)

  @Test
  fun getDataStoreReturnsRemoteDataStoreWhenForceRefreshed() {
    assertThat(articleDataStoreFactory.getDataStore(forceRefresh = true, isArticlesCached = true, isCacheExpired = true))
        .isEqualTo(articleRemoteDataStore)
  }

  @Test
  fun getDataStoreReturnsRemoteDataStoreWhenCacheExpired() {
    assertThat(articleDataStoreFactory.getDataStore(forceRefresh = false, isArticlesCached = true, isCacheExpired = true))
        .isEqualTo(articleRemoteDataStore)
  }

  @Test
  fun getDataStoreReturnsRemoteDataStoreWhenArticlesNotCached() {
    assertThat(articleDataStoreFactory.getDataStore(forceRefresh = true, isArticlesCached = false, isCacheExpired = true))
        .isEqualTo(articleRemoteDataStore)
  }

  @Test
  fun getDataStoreReturnsCacheDataStoreWhenArticlesCachedNotExpiredAndNotForceRefreshed() {
    assertThat(articleDataStoreFactory.getDataStore(forceRefresh = false, isArticlesCached = true, isCacheExpired = false))
        .isEqualTo(articleCacheDataStore)
  }

  @Test
  fun getCacheDataStoreReturnsCacheDataStore() {
    assertThat(articleDataStoreFactory.getCacheDataStore()).isEqualTo(articleCacheDataStore)
  }

}