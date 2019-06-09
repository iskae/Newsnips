package de.iskae.data.repository.cache.db

object ConfigConstants {
  const val TABLE_NAME = "config"
  const val COLUMN_COUNTRY_CODE = "country_code"
  const val COLUMN_CATEGORY = "category"
  const val QUERY_CONFIG_BY_COUNTRY_OR_CATEGORY = "SELECT * FROM $TABLE_NAME  WHERE $COLUMN_COUNTRY_CODE = :countryCode OR $COLUMN_CATEGORY = :category"
  const val QUERY_CONFIG_BY_COUNTRY_AND_CATEGORY = "SELECT * FROM $TABLE_NAME  WHERE $COLUMN_COUNTRY_CODE = :countryCode AND $COLUMN_CATEGORY = :category"
  const val QUERY_ALL_CONFIG = "SELECT * FROM $TABLE_NAME"

}