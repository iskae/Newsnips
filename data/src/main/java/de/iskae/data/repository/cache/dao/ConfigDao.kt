package de.iskae.data.repository.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.iskae.data.repository.cache.db.ConfigConstants
import de.iskae.data.repository.cache.model.Config
import io.reactivex.Flowable

@Dao
abstract class ConfigDao {

  @Query(ConfigConstants.QUERY_CONFIG)
  abstract fun getConfig(countryCode: String?, category: String?): Flowable<Config>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertConfig(config: Config)
}