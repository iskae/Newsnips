package de.iskae.presentation.topheadlines.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.domain.interactor.topheadlines.GetTopHeadlines
import de.iskae.domain.model.ArticleIdentifier
import de.iskae.presentation.mapper.ArticleViewMapper
import de.iskae.presentation.model.ArticleView
import de.iskae.presentation.state.Resource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * https://medium.com/@sharmadhiraj.np/android-paging-library-step-by-step-implementation-guide-75417753d9b9
 */
class ArticleDataSource @Inject constructor(
    private val getTopHeadlines: GetTopHeadlines,
    private val articleViewMapper: ArticleViewMapper,
    private val topHeadlinesPreferencesManager: TopHeadlinesPreferencesManager
) : PageKeyedDataSource<Int, ArticleView>() {

  companion object {
    const val INITIAL_PAGE = 1
  }

  private val compositeDisposable = CompositeDisposable()
  private var retryCompletable: Completable? = null

  var resource: MutableLiveData<Resource<ArticleView>> = MutableLiveData()

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleView>) {
    resource.postValue(Resource.Loading())
    topHeadlinesPreferencesManager.setLastRequestedPageNumber(INITIAL_PAGE)
    compositeDisposable.add(getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params.forCountryAndCategory(
        false,
        getArticleIdentifier()
    ))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ articles ->
          resource.postValue(Resource.Success(null))
          callback.onResult(articles.map { article -> articleViewMapper.mapToView(article) }, null, INITIAL_PAGE + 1)
        }, {
          resource.postValue(Resource.Error(it))
          setRetry(Action { loadInitial(params, callback) })
        }))
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleView>) {
    resource.postValue(Resource.Loading())
    topHeadlinesPreferencesManager.setLastRequestedPageNumber(params.key)
    compositeDisposable.add(getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params.forCountryAndCategory(
        false,
        getArticleIdentifier()
    ))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ articles ->
          resource.postValue(Resource.Success(null))
          callback.onResult(articles.map { article -> articleViewMapper.mapToView(article) }, params.key + 1)
        }, {
          resource.postValue(Resource.Error(it))
          setRetry(Action { loadAfter(params, callback) })
        }))
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleView>) {
    resource.postValue(Resource.Loading())
    topHeadlinesPreferencesManager.setLastRequestedPageNumber(params.key)
    compositeDisposable.add(getTopHeadlines.buildUseCaseObservable(GetTopHeadlines.Params.forCountryAndCategory(
        false,
        getArticleIdentifier()
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
        }, {
          resource.postValue(Resource.Error(it))
          setRetry(Action { loadBefore(params, callback) })
        }))
  }

  fun retry() {
    if (retryCompletable != null) {
      compositeDisposable.add(retryCompletable!!
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe())
    }
  }

  private fun setRetry(action: Action?) {
    retryCompletable = if (action == null) null else Completable.fromAction(action)
  }

  private fun getArticleIdentifier(): ArticleIdentifier {
    return ArticleIdentifier(topHeadlinesPreferencesManager.getCountryPreference(),
        topHeadlinesPreferencesManager.getCategoryPreference(),
        topHeadlinesPreferencesManager.getLastRequestedPageNumber())
  }

}