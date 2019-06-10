package de.iskae.domain.model

data class Article(
    val articleIdentifier: ArticleIdentifier,
    val source: String,
    val author: String?,
    val title: String,
    val description: String?,
    val directUrl: String,
    val imageUrl: String?,
    val publishedTime: String,
    val content: String?
)