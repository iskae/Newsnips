package de.iskae.domain.factory

import de.iskae.core.test.DataFactory.randomString
import de.iskae.domain.model.Article

object ArticleFactory {
  fun makeArticle(): Article {
    return Article(
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString()
    )
  }

  fun makeArticleList(count: Int): List<Article> {
    val articles = mutableListOf<Article>()
    repeat(count) {
      articles.add(makeArticle())
    }
    return articles
  }
}