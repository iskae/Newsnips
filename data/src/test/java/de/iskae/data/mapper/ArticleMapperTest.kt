package de.iskae.data.mapper

import de.iskae.data.factory.ArticleFactory.makeArticle
import de.iskae.data.factory.ArticleFactory.makeArticleEntity
import de.iskae.data.factory.ArticleFactory.makeArticleIdentifier
import de.iskae.data.model.ArticleEntity
import de.iskae.domain.model.Article
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ArticleMapperTest {

  private val mapper = ArticleMapper()

  @Test
  fun mapFromEntityMapsDataToDomain() {
    val articleEntity = makeArticleEntity(makeArticleIdentifier())
    val article = mapper.mapFromEntity(articleEntity)

    assertDataEquals(articleEntity, article)
  }

  @Test
  fun mapToEntityMapsDataFromDomain() {
    val article = makeArticle(makeArticleIdentifier())
    val articleEntity = mapper.mapToEntity(article)

    assertDataEquals(articleEntity, article)
  }

  private fun assertDataEquals(articleEntity: ArticleEntity, article: Article) {
    assertThat(articleEntity.articleIdentifier.page).isEqualTo(article.articleIdentifier.page)
    assertThat(articleEntity.articleIdentifier.countryCode).isEqualTo(article.articleIdentifier.countryCode)
    assertThat(articleEntity.articleIdentifier.category).isEqualTo(article.articleIdentifier.category)
    assertThat(articleEntity.description).isEqualTo(article.description)
    assertThat(articleEntity.author).isEqualTo(article.author)
    assertThat(articleEntity.content).isEqualTo(article.content)
    assertThat(articleEntity.imageUrl).isEqualTo(article.imageUrl)
    assertThat(articleEntity.directUrl).isEqualTo(article.directUrl)
    assertThat(articleEntity.title).isEqualTo(article.title)
    assertThat(articleEntity.publishedTime).isEqualTo(article.publishedTime)
    assertThat(articleEntity.source).isEqualTo(article.source)
  }
}