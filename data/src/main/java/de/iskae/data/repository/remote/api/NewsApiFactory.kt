package de.iskae.data.repository.remote.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val NEWS_API_BASE_URL = "https://newsapi.org"
const val TIMEOUT_SECONDS = 120L

class NewsApiFactory {
  fun buildNewsApi(isDebug: Boolean, apiKey: String): NewsApi {
    val okHttpClient = buildOkHttpClient(
        buildLoggingInterceptor((isDebug)),
        buildApiKeyInterceptor(apiKey)
    )
    return buildNewsApi(okHttpClient)
  }

  private fun buildNewsApi(okHttpClient: OkHttpClient): NewsApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(NEWS_API_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    return retrofit.create(NewsApi::class.java)
  }

  private fun buildOkHttpClient(
      httpLoggingInterceptor: HttpLoggingInterceptor,
      apiKeyInterceptor: Interceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(apiKeyInterceptor)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
  }

  private fun buildLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (isDebug) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }
    return logging
  }

  private fun buildApiKeyInterceptor(apiKey: String): Interceptor {
    return Interceptor { chain ->
      val original = chain.request()
      val originalHttpUrl = original.url()

      val url = originalHttpUrl.newBuilder()
          .addQueryParameter("apiKey", apiKey)
          .build()

      val requestBuilder = original.newBuilder()
          .url(url)

      val request = requestBuilder.build()
      return@Interceptor chain.proceed(request)
    }
  }
}
