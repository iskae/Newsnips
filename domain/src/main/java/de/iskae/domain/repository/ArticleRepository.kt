package de.iskae.domain.repository

import de.iskae.domain.model.Article
import io.reactivex.Observable

interface ArticleRepository {
  fun getTopHeadlinesForCountry(forceRefresh: Boolean, countryCode: String): Observable<List<Article>>
  fun getTopHeadlinesForCategory(forceRefresh: Boolean, category: String): Observable<List<Article>>
  fun getTopHeadlinesForCountryAndCategory(forceRefresh: Boolean, country: String, category: String): Observable<List<Article>>
}