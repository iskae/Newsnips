package de.iskae.domain.interactor.topheadlines

import de.iskae.core.constants.Category
import de.iskae.domain.executor.PostExecutionThread
import de.iskae.domain.interactor.ObservableUseCase
import de.iskae.domain.model.Article
import de.iskae.domain.repository.ArticleRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTopHeadlinesForCategory @Inject constructor(
    private val articleRepository: ArticleRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Article>, GetTopHeadlinesForCategory.Params?>(postExecutionThread) {

  override fun buildUseCaseObservable(params: Params?): Observable<List<Article>> {
    if (params == null) throw IllegalArgumentException("Params can't be null!")
    return articleRepository.getTopHeadlinesForCategory(params.forceRefresh, params.category)
  }

  data class Params constructor(val forceRefresh: Boolean, val category: String) {
    companion object {
      fun forCategory(forceRefresh: Boolean, category: Category): Params {
        return Params(forceRefresh, category.name)
      }
    }
  }
}