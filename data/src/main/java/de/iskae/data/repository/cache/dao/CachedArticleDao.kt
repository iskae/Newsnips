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
  @Query(ArticleDbConstants.QUERY_ARTICLES)
  abstract fun getArticles(countryCode: String?, category: String?): Flowable<List<CachedArticle>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertArticles(articles: List<CachedArticle>)

  @Query(ArticleDbConstants.DELETE_ARTICLES)
  abstract fun deleteArticles(countryCode: String?, category: String?)

  @Query(ArticleDbConstants.DELETE_ALL_ARTICLES)
  abstract fun deleteAllArticles()

}