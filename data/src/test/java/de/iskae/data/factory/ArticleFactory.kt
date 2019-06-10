package de.iskae.data.factory

import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.core.test.DataFactory.randomString
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.cache.model.CachedArticle
import de.iskae.data.repository.cache.model.Config
import de.iskae.data.repository.remote.model.ArticleResponseModel
import de.iskae.data.repository.remote.model.Source
import de.iskae.data.repository.remote.model.TopHeadlinesResponseModel
import de.iskae.domain.model.Article
import de.iskae.domain.model.ArticleIdentifier

object ArticleFactory {
  fun makeArticleEntity(articleIdentifier: ArticleIdentifier): ArticleEntity {
    return ArticleEntity(
        articleIdentifier = articleIdentifier,
        source = randomString(),
        author = randomString(),
        title = randomString(),
        description = randomString(),
        directUrl = randomString(),
        imageUrl = randomString(),
        publishedTime = randomString(),
        content = randomString()
    )
  }

  fun makeArticleEntityList(count: Int, articleIdentifier: ArticleIdentifier): List<ArticleEntity> {
    val articles = mutableListOf<ArticleEntity>()
    repeat(count) {
      articles.add(makeArticleEntity(articleIdentifier))
    }
    return articles
  }

  fun makeCachedArticle(articleIdentifier: ArticleIdentifier): CachedArticle {
    return CachedArticle(
        articleIdentifier = articleIdentifier,
        source = randomString(),
        author = randomString(),
        title = randomString(),
        description = randomString(),
        directUrl = randomString(),
        imageUrl = randomString(),
        publishedTime = randomString(),
        content = randomString()
    )
  }

  fun makeCachedArticleList(count: Int, articleIdentifier: ArticleIdentifier): List<CachedArticle> {
    val articles = mutableListOf<CachedArticle>()
    repeat(count) {
      articles.add(makeCachedArticle(articleIdentifier))
    }
    return articles
  }

  private fun makeSourceResponseModel(): Source {
    return Source(randomString(), randomString())
  }

  fun makeArticleResponseModel(): ArticleResponseModel {
    return ArticleResponseModel(
        source = makeSourceResponseModel(),
        author = randomString(),
        title = randomString(),
        description = randomString(),
        url = randomString(),
        urlToImage = randomString(),
        publishedAt = randomString(),
        content = randomString()
    )
  }

  fun makeTopHeadlinesResponseModel(count: Int): TopHeadlinesResponseModel {
    val articles = mutableListOf<ArticleResponseModel>()
    repeat(count) {
      articles.add(makeArticleResponseModel())
    }
    return TopHeadlinesResponseModel(randomString(), articles.size, articles)
  }

  fun makeConfig(articleIdentifier: ArticleIdentifier): Config {
    return Config(lastCacheTime = System.currentTimeMillis(), articleIdentifier = articleIdentifier)
  }

  fun makeArticle(articleIdentifier: ArticleIdentifier): Article {
    return Article(
        articleIdentifier = articleIdentifier,
        source = randomString(),
        author = randomString(),
        title = randomString(),
        description = randomString(),
        directUrl = randomString(),
        imageUrl = randomString(),
        publishedTime = randomString(),
        content = randomString()
    )
  }

  fun makeArticleIdentifier(): ArticleIdentifier {
    return ArticleIdentifier(Country.DE.name, Category.BUSINESS.name, 0)
  }
}