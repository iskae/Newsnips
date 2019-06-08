package de.iskae.data.remote.mapper

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.test.DataFactory.randomString
import de.iskae.data.core.topheadlines.TopHeadlinesPreferencesManager
import de.iskae.data.factory.ArticleFactory.makeArticleResponseModel
import de.iskae.data.repository.remote.mapper.ArticleResponseModelMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ArticleResponseModelMapperTest {

  private val topHeadlinesPreferencesManager = mock<TopHeadlinesPreferencesManager>()
  private val articleResponseModelMapper = ArticleResponseModelMapper(topHeadlinesPreferencesManager)

  @Test
  fun mapFromResponseModelMapsDataToEntity() {
    val responseModel = makeArticleResponseModel()
    val articleEntity = articleResponseModelMapper.mapFromModel(responseModel)

    assertThat(responseModel.author).isEqualTo(articleEntity.author)
    assertThat(responseModel.content).isEqualTo(articleEntity.content)
    assertThat(responseModel.description).isEqualTo(articleEntity.description)
    assertThat(responseModel.publishedAt).isEqualTo(articleEntity.publishedTime)
    assertThat(responseModel.title).isEqualTo(articleEntity.title)
    assertThat(responseModel.url).isEqualTo(articleEntity.directUrl)
    assertThat(responseModel.urlToImage).isEqualTo(articleEntity.imageUrl)
    assertThat(responseModel.sourceResponseModel.name).isEqualTo(articleEntity.source)
    assertThat(articleEntity.category).isNull()
    assertThat(articleEntity.countryCode).isNull()
  }

  @Test
  fun mapFromResponseModelMapsDataFromTopHeadlinesPreferencesManager() {
    val countryCode = randomString()
    val category = randomString()
    whenever(topHeadlinesPreferencesManager.getCountryPreference())
        .thenReturn(countryCode)
    whenever(topHeadlinesPreferencesManager.getCategoryPreference())
        .thenReturn(category)

    val responseModel = makeArticleResponseModel()
    val articleEntity = articleResponseModelMapper.mapFromModel(responseModel)

    assertThat(articleEntity.countryCode).isEqualTo(countryCode)
    assertThat(articleEntity.category).isEqualTo(category)
  }
}