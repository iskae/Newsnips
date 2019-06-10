package de.iskae.presentation.topheadlines.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.domain.interactor.topheadlines.GetTopHeadlines
import de.iskae.presentation.mapper.ArticleViewMapper
import de.iskae.presentation.model.ArticleView
import javax.inject.Inject

class ArticleDataSourceFactory @Inject constructor(
    private val getTopHeadlines: GetTopHeadlines,
    private val articleViewMapper: ArticleViewMapper,
    private val topHeadlinesPreferencesManager: TopHeadlinesPreferencesManager
) : DataSource.Factory<Int, ArticleView>() {

  val articlesDataSource = MutableLiveData<ArticleDataSource>()

  override fun create(): DataSource<Int, ArticleView> {
    val articleDataSource = ArticleDataSource(getTopHeadlines, articleViewMapper, topHeadlinesPreferencesManager)
    articlesDataSource.postValue(articleDataSource)
    return articleDataSource
  }

}