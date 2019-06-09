package de.iskae.presentation.topheadlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.domain.interactor.topheadlines.GetTopHeadlines
import de.iskae.domain.model.Article
import de.iskae.presentation.mapper.ArticleViewMapper
import de.iskae.presentation.model.ArticleView
import de.iskae.presentation.state.Resource
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class TopHeadlinesViewModel @Inject constructor(
  private val getTopHeadlines: GetTopHeadlines?,
  private val articleViewMapper: ArticleViewMapper,
  private val topHeadlinesPreferencesManager: TopHeadlinesPreferencesManager
) : ViewModel() {

  private val articlesData = MutableLiveData<Resource<List<ArticleView>>>()

  init {
    setCountry(Country.DE)
    fetchTopHeadlines()
  }

  fun getArticles(): LiveData<Resource<List<ArticleView>>> {
    return articlesData
  }

  private fun fetchTopHeadlines() {
    articlesData.postValue(Resource.Loading())
    val countryValue = topHeadlinesPreferencesManager.getCountryPreference()?.let { country ->
      Country.valueOf(country)
    }
    val categoryValue = topHeadlinesPreferencesManager.getCategoryPreference()?.let { category ->
      Category.valueOf(category)
    }
    getTopHeadlines?.execute(
      LiveArticlesSubscriber(),
      GetTopHeadlines.Params.forCountryAndCategory(
        false,
        countryValue,
        categoryValue
      )
    )
  }

  fun setCountry(country: Country) {
    topHeadlinesPreferencesManager.setCountryPreference(country)
  }

  fun setCategory(category: Category) {
    topHeadlinesPreferencesManager.setCategoryPreference(category)
  }

  inner class LiveArticlesSubscriber : DisposableObserver<List<Article>>() {
    override fun onNext(articles: List<Article>) {
      articlesData.postValue(Resource.Success(articles.map { article -> articleViewMapper.mapToView(article) }))
    }

    override fun onComplete() {}
    override fun onError(throwable: Throwable) {
      articlesData.postValue(Resource.Error(throwable))
    }
  }

}