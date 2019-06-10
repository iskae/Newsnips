package de.iskae.data.store

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleDataStore
import de.iskae.data.repository.ArticleRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ArticleRemoteDataStore @Inject constructor(private val articleRemote: ArticleRemote) : ArticleDataStore {

  override fun getTopHeadlines(country: Country?, category: Category?, page: Int): Observable<List<ArticleEntity>> {
    return articleRemote.getTopHeadlines(country, category, page)
  }

  override fun saveTopHeadlines(country: Country?, category: Category?, articles: List<ArticleEntity>): Completable {
    throw UnsupportedOperationException("Saving top headlines is not possible in remote store")
  }

  override fun clearTopHeadlines(country: Country?, category: Category?): Completable {
    throw UnsupportedOperationException("Clearing top headlines is not possible in remote store")
  }
}