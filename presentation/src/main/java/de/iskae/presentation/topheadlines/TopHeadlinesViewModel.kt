package de.iskae.presentation.topheadlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.core.constants.PAGE_SIZE
import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.domain.interactor.topheadlines.GetTopHeadlines
import de.iskae.presentation.mapper.ArticleViewMapper
import de.iskae.presentation.model.ArticleView
import de.iskae.presentation.state.Resource
import de.iskae.presentation.topheadlines.adapter.ArticleDataSource
import de.iskae.presentation.topheadlines.adapter.ArticleDataSourceFactory
import javax.inject.Inject

class TopHeadlinesViewModel @Inject constructor(
    getTopHeadlines: GetTopHeadlines,
    articleViewMapper: ArticleViewMapper,
    private val topHeadlinesPreferencesManager: TopHeadlinesPreferencesManager
) : ViewModel() {

  val articlesData: LiveData<PagedList<ArticleView>>
  private val articleDataSourceFactory: ArticleDataSourceFactory

  init {
    setCountry(Country.US)
    articleDataSourceFactory = ArticleDataSourceFactory(getTopHeadlines, articleViewMapper, topHeadlinesPreferencesManager)
    val config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setInitialLoadSizeHint(PAGE_SIZE * 2)
        .setEnablePlaceholders(true)
        .build()
    articlesData = LivePagedListBuilder<Int, ArticleView>(articleDataSourceFactory, config).build()
  }

  fun getResource(): LiveData<Resource<ArticleView>> = Transformations.switchMap<ArticleDataSource,
      Resource<ArticleView>>(articleDataSourceFactory.articlesDataSource, ArticleDataSource::resource)

  fun setCountry(country: Country) {
    topHeadlinesPreferencesManager.setCountryPreference(country)
  }

  fun setCategory(category: Category) {
    topHeadlinesPreferencesManager.setCategoryPreference(category)
  }

  fun retry() {
    articleDataSourceFactory.articlesDataSource.value?.retry()
  }

}