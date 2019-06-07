package de.iskae.domain.interactor.topheadlines

import de.iskae.core.constants.Country
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.interactor.ObservableUseCase
import de.iskae.domain.model.Article
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTopHeadlinesForCountry @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Article>, GetTopHeadlinesForCountry.Params?>(postExecutionThread) {

  override fun buildUseCaseObservable(params: Params?): Observable<List<Article>> {
    if (params == null) throw IllegalArgumentException("Params can't be null!")
    return articleRepository.getTopHeadlinesForCountry(params.forceRefresh, params.country)
  }

  data class Params constructor(val forceRefresh: Boolean, val country: String) {
    companion object {
      fun forCountry(forceRefresh: Boolean, country: Country): Params {
        return Params(forceRefresh, country.name)
      }
    }
  }
}