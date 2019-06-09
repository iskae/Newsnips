package de.iskae.presentation.di.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.iskae.data.ArticleDataRepository
import de.iskae.data.repository.ArticleCache
import de.iskae.data.repository.ArticleRemote
import de.iskae.data.repository.cache.ArticleCacheImpl
import de.iskae.data.repository.cache.db.ArticleDatabase
import de.iskae.data.repository.remote.ArticleRemoteImpl
import de.iskae.data.repository.remote.api.NewsApi
import de.iskae.data.repository.remote.api.NewsApiFactory
import de.iskae.domain.repository.ArticleRepository
import de.iskae.presentation.BuildConfig

@Module
abstract class DataModule {

  @Module
  companion object {
    @Provides
    @JvmStatic
    fun providesDatabase(application: Application): ArticleDatabase {
      return ArticleDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    fun provideNewsApi(): NewsApi {
      return NewsApiFactory().buildNewsApi(
        BuildConfig.DEBUG,
        BuildConfig.NEWS_API_KEY
      )
    }
  }

  @Binds
  abstract fun bindArticleCache(articleCache: ArticleCacheImpl): ArticleCache

  @Binds
  abstract fun bindArticleRemote(articleRemote: ArticleRemoteImpl): ArticleRemote

  @Binds
  abstract fun bindDataRepository(dataRepository: ArticleDataRepository): ArticleRepository
}