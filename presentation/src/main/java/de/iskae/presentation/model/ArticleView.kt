package de.iskae.presentation.model

data class ArticleView(
  val source: String,
  val author: String?,
  val title: String,
  val description: String?,
  val imageUrl: String?,
  val publishedHourDifference: Int,
  val content: String?
)