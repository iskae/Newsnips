package de.iskae.domain.repository

import de.iskae.domain.model.Article
import io.reactivex.Observable

interface ArticleRepository {
  fun getTopHeadlines(forceRefresh: Boolean, countryCode: String?, category: String?): Observable<List<Article>>
}