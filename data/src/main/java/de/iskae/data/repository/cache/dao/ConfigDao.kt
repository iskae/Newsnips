package de.iskae.data.repository.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.iskae.data.repository.cache.db.ConfigConstants
import de.iskae.data.repository.cache.model.Config
import de.iskae.domain.model.ArticleIdentifier
import io.reactivex.Maybe

@Dao
abstract class ConfigDao {

  @Query(ConfigConstants.QUERY_CONFIG)
  abstract fun getConfig(articleIdentifier: ArticleIdentifier): Maybe<Config>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertConfig(config: Config)
}