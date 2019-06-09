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

object ArticleFactory {
  fun makeArticleEntity(): ArticleEntity {
    return ArticleEntity(
        Country.DE.name,
        Category.BUSINESS.name,
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

  fun makeArticleEntityList(count: Int): List<ArticleEntity> {
    val articles = mutableListOf<ArticleEntity>()
    repeat(count) {
      articles.add(makeArticleEntity())
    }
    return articles
  }

  fun makeCachedArticle(): CachedArticle {
    return CachedArticle(
        Country.DE.name,
        Category.BUSINESS.name,
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

  fun makeCachedArticleList(count: Int): List<CachedArticle> {
    val articles = mutableListOf<CachedArticle>()
    repeat(count) {
      articles.add(makeCachedArticle())
    }
    return articles
  }

  fun makeSourceResponseModel(): Source {
    return Source(randomString(), randomString())
  }

  fun makeArticleResponseModel(): ArticleResponseModel {
    return ArticleResponseModel(
        makeSourceResponseModel(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString()
    )
  }

  fun makeTopHeadlinesResponseModel(count: Int): TopHeadlinesResponseModel {
    val articles = mutableListOf<ArticleResponseModel>()
    repeat(count) {
      articles.add(makeArticleResponseModel())
    }
    return TopHeadlinesResponseModel(randomString(), articles.size, articles)
  }

  fun makeConfig(): Config {
    return Config(lastCacheTime = System.currentTimeMillis(), countryCode = Country.DE.name, category = Category.BUSINESS.name)
  }

  fun makeArticle(): Article {
    return Article(
        Country.DE.name,
        Category.BUSINESS.name,
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
}