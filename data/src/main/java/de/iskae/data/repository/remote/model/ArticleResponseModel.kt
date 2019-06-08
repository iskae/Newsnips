package de.iskae.data.repository.remote.model

data class ArticleResponseModel(
    val sourceResponseModel: SourceResponseModel,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)