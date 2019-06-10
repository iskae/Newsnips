package de.iskae.presentation.topheadlines.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.domain.interactor.topheadlines.GetTopHeadlines
import de.iskae.presentation.mapper.ArticleViewMapper
import de.iskae.presentation.model.ArticleView
import de.iskae.presentation.state.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    private val getTopHeadlines: GetTopHeadlines,
    private val articleViewMapper: ArticleViewMapper,
    private val topHeadlinesPreferencesManager: TopHeadlinesPreferencesManager
) : PageKeyedDataSource<Int, ArticleView>() {

  companion object {
    const val INITIAL_PAGE = 0
  }

  private val compositeDisposable = CompositeDisposable()

  var resource: MutableLiveData<Resource<ArticleView>> = MutableLiveData()

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleView>) {
    resource.postValue(Resource.Loading())
    val requestParams = getRequestParameters()
    compositeDisposable.add(getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params.forCountryAndCategory(
        false,
        requestParams.first,
        requestParams.second,
        INITIAL_PAGE
    ))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ articles ->
          resource.postValue(Resource.Success(null))
          callback.onResult(articles.map { article -> articleViewMapper.mapToView(article) }, null, INITIAL_PAGE + 1)
        }, { resource.postValue(Resource.Error(it)) }))
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleView>) {
    resource.postValue(Resource.Loading())
    val requestParams = getRequestParameters()
    compositeDisposable.add(getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params.forCountryAndCategory(
        false,
        requestParams.first,
        requestParams.second,
        params.key
    ))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ articles ->
          resource.postValue(Resource.Success(null))
          callback.onResult(articles.map { article -> articleViewMapper.mapToView(article) }, params.key + 1)
        }, { resource.postValue(Resource.Error(it)) }))
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleView>) {
    resource.postValue(Resource.Loading())
    val requestParams = getRequestParameters()
    compositeDisposable.add(getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params.forCountryAndCategory(
        false,
        requestParams.first,
        requestParams.second,
        params.key
    ))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ articles ->
          resource.postValue(Resource.Success(null))
          callback.onResult(articles.map { article -> articleViewMapper.mapToView(article) }, if (params.key > 0) {
            params.key - 1
          } else {
            null
          })
        }, { resource.postValue(Resource.Error(it)) }))
  }

  private fun getRequestParameters(): Pair<Country?, Category?> {
    val country = topHeadlinesPreferencesManager.getCountryPreference()?.let { country ->
      Country.valueOf(country)
    }
    val category = topHeadlinesPreferencesManager.getCategoryPreference()?.let { category ->
      Category.valueOf(category)
    }
    return Pair(country, category)
  }

}