package de.iskae.data.repository.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.iskae.data.repository.cache.db.ConfigConstants
import de.iskae.domain.model.ArticleIdentifier

@Entity(tableName = ConfigConstants.TABLE_NAME)
data class Config(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var lastCacheTime: Long,
    @ColumnInfo(name = ConfigConstants.COLUMN_ARTICLE_IDENTIFIER)
    val articleIdentifier: ArticleIdentifier
)