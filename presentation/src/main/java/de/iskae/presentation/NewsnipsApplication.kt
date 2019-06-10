package de.iskae.presentation

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import de.iskae.presentation.di.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

class NewsnipsApplication : Application(), HasActivityInjector {

  @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    DaggerApplicationComponent
        .builder()
        .application(this)
        .build()
        .inject(this)
  }
}