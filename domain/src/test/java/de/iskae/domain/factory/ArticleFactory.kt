package de.iskae.domain.factory

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.core.test.DataFactory.randomString
import de.iskae.domain.model.Article
import de.iskae.domain.model.ArticleIdentifier

object ArticleFactory {
  private fun makeArticle(articleIdentifier: ArticleIdentifier): Article {
    return Article(
        articleIdentifier = articleIdentifier,
        source = randomString(),
        author = null,
        title = randomString(),
        description = null,
        directUrl = randomString(),
        imageUrl = null,
        publishedTime = randomString(),
        content = null
    )
  }

  fun makeArticleIdentifier(): ArticleIdentifier {
    return ArticleIdentifier(
        countryCode = Country.DE.name,
        category = Category.BUSINESS.name,
        page = 0
    )
  }

  fun makeArticleList(count: Int, articleIdentifier: ArticleIdentifier): List<Article> {
    val articles = mutableListOf<Article>()
    repeat(count) {
      articles.add(makeArticle(articleIdentifier))
    }
    return articles
  }
}