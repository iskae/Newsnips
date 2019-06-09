package de.iskae.data.repository.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.iskae.data.repository.cache.db.ConfigConstants
import de.iskae.data.repository.cache.model.Config
import io.reactivex.Maybe

@Dao
abstract class ConfigDao {

  @Query(ConfigConstants.QUERY_CONFIG_BY_COUNTRY_OR_CATEGORY)
  abstract fun getConfigByCountryOrCategory(countryCode: String?, category: String?): Maybe<Config>

  @Query(ConfigConstants.QUERY_CONFIG_BY_COUNTRY_AND_CATEGORY)
  abstract fun getConfigByCountryAndCategory(countryCode: String, category: String): Maybe<Config>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertConfig(config: Config)
}