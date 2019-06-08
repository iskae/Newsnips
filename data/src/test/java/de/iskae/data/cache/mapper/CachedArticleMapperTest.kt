package de.iskae.data.cache.mapper

import de.iskae.data.factory.ArticleFactory.makeArticleEntity
import de.iskae.data.factory.ArticleFactory.makeCachedArticle
import de.iskae.data.model.ArticleEntity
import de.iskae.data.repository.cache.mapper.CachedArticleMapper
import de.iskae.data.repository.cache.model.CachedArticle
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CachedArticleMapperTest {

  private val mapper = CachedArticleMapper()

  @Test
  fun mapFromCachedMapsDataToEntity() {
    val cachedArticle = makeCachedArticle()
    val articleEntity = mapper.mapToEntity(cachedArticle)
    assertEqualData(cachedArticle, articleEntity)
  }

  @Test
  fun mapToCachedMapsDataFromEntity() {
    val articleEntity = makeArticleEntity()
    val cachedArticle = mapper.mapFromEntity(articleEntity)

    assertEqualData(cachedArticle, articleEntity)
  }

  private fun assertEqualData(cachedArticle: CachedArticle, articleEntity: ArticleEntity) {
    assertThat(cachedArticle.author).isEqualTo(articleEntity.author)
    assertThat(cachedArticle.category).isEqualTo(articleEntity.category)
    assertThat(cachedArticle.countryCode).isEqualTo(articleEntity.countryCode)
    assertThat(cachedArticle.content).isEqualTo(articleEntity.content)
    assertThat(cachedArticle.description).isEqualTo(articleEntity.description)
    assertThat(cachedArticle.directUrl).isEqualTo(articleEntity.directUrl)
    assertThat(cachedArticle.imageUrl).isEqualTo(articleEntity.imageUrl)
    assertThat(cachedArticle.publishedTime).isEqualTo(articleEntity.publishedTime)
    assertThat(cachedArticle.source).isEqualTo(articleEntity.source)
    assertThat(cachedArticle.title).isEqualTo(articleEntity.title)
  }
}