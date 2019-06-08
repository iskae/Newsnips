package de.iskae.data.repository.remote.api

import de.iskae.data.repository.remote.model.TopHeadlinesResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

  @GET("v2/top-headlines")
  fun getTopHeadlines(
      @Query("country") countryCode: String?,
      @Query("category") category: String?
  ): Observable<TopHeadlinesResponseModel>
}