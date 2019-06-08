package de.iskae.data.model

data class ArticleEntity(
    val countryCode: String?,
    val category: String?,
    val source: String,
    val author: String,
    val title: String,
    val description: String,
    val directUrl: String,
    val imageUrl: String,
    val publishedTime: String,
    val content: String
)