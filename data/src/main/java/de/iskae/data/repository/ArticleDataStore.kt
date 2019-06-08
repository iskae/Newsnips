package de.iskae.data.repository

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.model.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface ArticleDataStore {
  fun getTopHeadlines(country: Country?, category: Category?): Observable<List<ArticleEntity>>
  fun saveTopHeadlines(country: Country?, category: Category?, articles: List<ArticleEntity>): Completable
  fun clearTopHeadlines(country: Country?, category: Category?): Completable
}