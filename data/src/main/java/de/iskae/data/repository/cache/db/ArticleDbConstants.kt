package de.iskae.data.repository.cache.db

object ArticleDbConstants {
  const val CACHE_EXPIRATION_MINUTES = 15L
  const val TABLE_NAME = "top_headlines"
  const val DATABASE_NAME = "news.db"
  const val COLUMN_ARTICLE_URL = "article_url"
  const val COLUMN_ARTICLE_IDENTIFIER = "article_identifier"
  const val QUERY_ARTICLES = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ARTICLE_IDENTIFIER = :articleIdentifier"
  const val DELETE_ARTICLES = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ARTICLE_IDENTIFIER = :articleIdentifier"
  const val DELETE_ALL_ARTICLES = "DELETE FROM $TABLE_NAME"
}