package de.iskae.data.repository

import de.iskae.data.model.ArticleEntity
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Observable

interface ArticleRemote {
  fun getTopHeadlines(articleIdentifier: ArticleIdentifier): Observable<List<ArticleEntity>>
}