package de.iskae.data.store

import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.ArticleDataStore
import de.iskae.data.repository.ArticleRemote
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ArticleRemoteDataStore @Inject constructor(private val articleRemote: ArticleRemote) : ArticleDataStore {

  override fun getTopHeadlines(articleIdentifier: ArticleIdentifier): Observable<List<ArticleEntity>> {
    return articleRemote.getTopHeadlines(articleIdentifier)
  }

  override fun saveTopHeadlines(articleIdentifier: ArticleIdentifier, articles: List<ArticleEntity>): Completable {
    throw UnsupportedOperationException("Saving top headlines is not possible in remote store")
  }

  override fun clearTopHeadlines(articleIdentifier: ArticleIdentifier): Completable {
    throw UnsupportedOperationException("Clearing top headlines is not possible in remote store")
  }
}