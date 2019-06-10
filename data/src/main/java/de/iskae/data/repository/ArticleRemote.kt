package de.iskae.data.repository

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.model.ArticleEntity
import io.reactivex.Observable

interface ArticleRemote {
  fun getTopHeadlines(country: Country?, category: Category?, page: Int): Observable<List<ArticleEntity>>
}