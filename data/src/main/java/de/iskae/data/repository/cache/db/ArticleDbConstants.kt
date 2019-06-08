package de.iskae.data.repository.cache.db

object ArticleDbConstants {
  const val TABLE_NAME = "top_headlines"
  const val DATABASE_NAME = "news.db"
  const val COLUMN_ARTICLE_URL = "article_url"
  const val COLUMN_COUNTRY_CODE = "country_code"
  const val COLUMN_CATEGORY = "category"
  const val QUERY_ARTICLES = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_COUNTRY_CODE = :countryCode AND $COLUMN_CATEGORY = :category"
  const val DELETE_ARTICLES = "DELETE FROM $TABLE_NAME WHERE $COLUMN_COUNTRY_CODE = :countryCode AND $COLUMN_CATEGORY = :category"
  const val DELETE_ALL_ARTICLES = "DELETE FROM $TABLE_NAME"
}