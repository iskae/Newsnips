package de.iskae.data.repository.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.iskae.data.repository.cache.db.ArticleDbConstants
import de.iskae.data.repository.cache.model.CachedArticle
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Flowable

@Dao
abstract class CachedArticleDao {

  @Query(ArticleDbConstants.QUERY_ARTICLES)
  abstract fun getArticles(articleIdentifier: ArticleIdentifier): Flowable<List<CachedArticle>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  abstract fun insertArticles(articles: List<CachedArticle>)

  @Query(ArticleDbConstants.DELETE_ARTICLES)
  abstract fun deleteArticles(articleIdentifier: ArticleIdentifier)

  @Query(ArticleDbConstants.DELETE_ALL_ARTICLES)
  abstract fun deleteAllArticles()

}