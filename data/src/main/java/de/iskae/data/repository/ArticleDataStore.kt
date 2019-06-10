package de.iskae.data.repository

import de.iskae.data.model.ArticleEntity
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Completable
import io.reactivex.Observable

interface ArticleDataStore {
  fun getTopHeadlines(articleIdentifier: ArticleIdentifier): Observable<List<ArticleEntity>>
  fun saveTopHeadlines(articleIdentifier: ArticleIdentifier, articles: List<ArticleEntity>): Completable
  fun clearTopHeadlines(articleIdentifier: ArticleIdentifier): Completable
}