package de.iskae.data.repository.cache.db

object ConfigConstants {
  const val TABLE_NAME = "config"
  const val COLUMN_COUNTRY_CODE = "country_code"
  const val COLUMN_CATEGORY = "category"
  const val QUERY_CONFIG = "SELECT * FROM $TABLE_NAME  WHERE $COLUMN_COUNTRY_CODE = :countryCode AND $COLUMN_CATEGORY = :category"
  const val QUERY_ALL_CONFIG = "SELECT * FROM $TABLE_NAME"

}