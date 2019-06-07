package de.iskae.domain.interactor.topheadlines

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.interactor.ObservableUseCase
import de.iskae.domain.model.Article
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTopHeadlinesForCountryAndCategory @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Article>, GetTopHeadlinesForCountryAndCategory.Params?>(postExecutionThread) {

  override fun buildUseCaseObservable(params: Params?): Observable<List<Article>> {
    if (params == null) throw IllegalArgumentException("Params can't be null!")
    return articleRepository.getTopHeadlinesForCountryAndCategory(params.forceRefresh, params.country, params.category)
  }

  data class Params constructor(val forceRefresh: Boolean, val country: String, val category: String) {
    companion object {
      fun forCountryAndCategory(forceRefresh: Boolean, country: Country, category: Category): Params {
        return Params(forceRefresh, country.name, category.name)
      }
    }
  }
}