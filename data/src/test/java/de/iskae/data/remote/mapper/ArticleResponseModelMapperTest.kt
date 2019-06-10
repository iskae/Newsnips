package de.iskae.data.remote.mapper

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.iskae.core.test.DataFactory.randomInt
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
    assertThat(responseModel.source.name).isEqualTo(articleEntity.source)
    assertThat(articleEntity.articleIdentifier).isNotNull
  }

  @Test
  fun mapFromResponseModelMapsDataFromTopHeadlinesPreferencesManager() {
    val countryCode = randomString()
    val category = randomString()
    val pageNumber = randomInt()
    whenever(topHeadlinesPreferencesManager.getCountryPreference())
        .thenReturn(countryCode)
    whenever(topHeadlinesPreferencesManager.getCategoryPreference())
        .thenReturn(category)
    whenever(topHeadlinesPreferencesManager.getLastRequestedPageNumber())
        .thenReturn(pageNumber)

    val responseModel = makeArticleResponseModel()
    val articleEntity = articleResponseModelMapper.mapFromModel(responseModel)

    assertThat(articleEntity.articleIdentifier.countryCode).isEqualTo(countryCode)
    assertThat(articleEntity.articleIdentifier.category).isEqualTo(category)
    assertThat(articleEntity.articleIdentifier.page).isEqualTo(pageNumber)
  }
}