package de.iskae.domain.interactor.topheadlines

import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.interactor.ObservableUseCase
import de.iskae.domain.model.Article
import de.iskae.domain.model.ArticleIdentifier
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTopHeadlines @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Article>, GetTopHeadlines.Params?>(postExecutionThread) {

  override fun buildUseCaseObservable(params: Params?): Observable<List<Article>> {
    if (params == null) {
      throw IllegalArgumentException("Params can't be null!")
    }
    if (params.articleIdentifier.countryCode == null && params.articleIdentifier.category == null) {
      throw  IllegalArgumentException("Parameter required! Please provide either a country or a category")
    }
    return articleRepository.getTopHeadlines(params.forceRefresh, params.articleIdentifier)
  }

  data class Params constructor(val forceRefresh: Boolean, val articleIdentifier: ArticleIdentifier) {
    companion object {
      fun forCountryAndCategory(forceRefresh: Boolean, articleIdentifier: ArticleIdentifier): Params {
        return Params(forceRefresh, articleIdentifier)
      }
    }
  }
}