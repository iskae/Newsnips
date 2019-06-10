package de.iskae.presentation.mapper

import de.iskae.core.util.TimeHelpers
import de.iskae.domain.model.Article
import de.iskae.presentation.model.ArticleView
import javax.inject.Inject

class ArticleViewMapper @Inject constructor() : ViewMapper<ArticleView, Article> {
  override fun mapToView(domain: Article): ArticleView {
    return ArticleView(
        domain.source,
        domain.author,
        domain.title,
        domain.description,
        domain.imageUrl,
        domain.directUrl,
        TimeHelpers.getHourDifferenceFromPublishTime(domain.publishedTime),
        domain.content
    )
  }
}