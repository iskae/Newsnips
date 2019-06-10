package de.iskae.data.repository.cache.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.iskae.domain.model.ArticleIdentifier

/**
 *https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9
 */
class Converters {

  private val gson = Gson()

  @TypeConverter
  fun fromArticleIdentifier(articleIdentifier: ArticleIdentifier): String {
    return gson.toJson(articleIdentifier)
  }

  @TypeConverter
  fun toArticleIdentifier(jsonString: String): ArticleIdentifier {
    val type = object : TypeToken<ArticleIdentifier>() {}.type
    return gson.fromJson(jsonString, type)
  }

}