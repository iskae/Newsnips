package de.iskae.data.repository.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.iskae.data.repository.cache.db.ArticleDbConstants

@Entity(tableName = ArticleDbConstants.TABLE_NAME)
data class CachedArticle(
    @ColumnInfo(name = ArticleDbConstants.COLUMN_COUNTRY_CODE)
    val countryCode: String?,
    @ColumnInfo(name = ArticleDbConstants.COLUMN_CATEGORY)
    val category: String?,
    val source: String,
    val author: String,
    val title: String,
    val description: String,
    @PrimaryKey
    @ColumnInfo(name = ArticleDbConstants.COLUMN_ARTICLE_URL)
    val directUrl: String,
    val imageUrl: String,
    val publishedTime: String,
    val content: String
)