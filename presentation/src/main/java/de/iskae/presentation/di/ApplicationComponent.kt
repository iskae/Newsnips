package de.iskae.presentation.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import de.iskae.presentation.NewsnipsApplication
import de.iskae.presentation.di.modules.ApplicationModule
import de.iskae.presentation.di.modules.DataModule
import de.iskae.presentation.di.modules.PresentationModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, DataModule::class, PresentationModule::class]
)
interface ApplicationComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): ApplicationComponent
  }

  fun inject(app: NewsnipsApplication)
}