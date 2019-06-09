package de.iskae.data.repository.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.iskae.data.repository.cache.db.ArticleDbConstants
import de.iskae.data.repository.cache.model.CachedArticle
import io.reactivex.Flowable

@Dao
abstract class CachedArticleDao {
  @Query(ArticleDbConstants.QUERY_ARTICLES_BY_COUNTRY_OR_CATEGORY)
  abstract fun getArticlesByCountryOrCategory(countryCode: String?, category: String?): Flowable<List<CachedArticle>>

  @Query(ArticleDbConstants.QUERY_ARTICLES_BY_COUNTRY_AND_CATEGORY)
  abstract fun getArticlesByCountryAndCategory(countryCode: String, category: String): Flowable<List<CachedArticle>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  abstract fun insertArticles(articles: List<CachedArticle>)

  @Query(ArticleDbConstants.DELETE_ARTICLES_BY_COUNTRY)
  abstract fun deleteArticlesByCountry(countryCode: String)

  @Query(ArticleDbConstants.DELETE_ARTICLES_BY_CATEGORY)
  abstract fun deleteArticlesByCategory(category: String)

  @Query(ArticleDbConstants.DELETE_ARTICLES_BY_COUNTRY_AND_CATEGORY)
  abstract fun deleteArticlesByCountryAndCategory(countryCode: String, category: String)

  @Query(ArticleDbConstants.DELETE_ALL_ARTICLES)
  abstract fun deleteAllArticles()

}