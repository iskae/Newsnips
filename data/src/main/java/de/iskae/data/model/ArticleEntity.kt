package de.iskae.data.model

import de.iskae.domain.model.ArticleIdentifier

data class ArticleEntity(
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