package de.iskae.data.repository.cache.db

object ConfigConstants {
  const val TABLE_NAME = "config"
  const val COLUMN_ARTICLE_IDENTIFIER = "article_identifier"
  const val QUERY_CONFIG = "SELECT * FROM $TABLE_NAME  WHERE $COLUMN_ARTICLE_IDENTIFIER = :articleIdentifier"

}