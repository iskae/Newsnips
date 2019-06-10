package de.iskae.presentation.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

  @Provides
  @Singleton
  fun provideContext(application: Application): Context {
    return application
  }

  @Provides
  @Singleton
  fun provideSharedPreferences(application: Application): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(application)
  }
}