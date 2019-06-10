package de.iskae.domain.repository

import de.iskae.domain.model.Article
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Observable

interface ArticleRepository {
  fun getTopHeadlines(forceRefresh: Boolean, articleIdentifier: ArticleIdentifier): Observable<List<Article>>
}