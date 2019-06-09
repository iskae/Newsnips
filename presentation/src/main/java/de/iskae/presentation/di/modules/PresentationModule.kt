package de.iskae.presentation.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.presentation.MainActivity
import de.iskae.presentation.UiThread
import de.iskae.presentation.topheadlines.TopHeadlinesFragment
import de.iskae.presentation.topheadlines.TopHeadlinesViewModel

@Module
abstract class PresentationModule {

  @Binds
  abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(TopHeadlinesViewModel::class)
  abstract fun bindTopHeadlinesViewModel(viewModel: TopHeadlinesViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun contributesMainActivity(): MainActivity

  @ContributesAndroidInjector
  abstract fun contributesTopHeadlinesFragment(): TopHeadlinesFragment

}