package de.iskae.data.repository.remote.model

data class TopHeadlinesResponseModel(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleResponseModel>
)