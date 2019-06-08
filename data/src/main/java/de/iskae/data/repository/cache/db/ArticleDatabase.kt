package de.iskae.data.repository.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.iskae.data.repository.cache.dao.CachedArticleDao
import de.iskae.data.repository.cache.dao.ConfigDao
import de.iskae.data.repository.cache.model.CachedArticle
import de.iskae.data.repository.cache.model.Config
import javax.inject.Inject

@Database(entities = [CachedArticle::class, Config::class], version = 1)
abstract class ArticleDatabase @Inject constructor() : RoomDatabase() {

  abstract fun cachedArticleDao(): CachedArticleDao

  abstract fun configDao(): ConfigDao

  companion object {
    private var INSTANCE: ArticleDatabase? = null
    private var lock = Any()
    fun getInstance(context: Context): ArticleDatabase {
      if (INSTANCE == null) {
        synchronized(lock) {
          if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context,
                ArticleDatabase::class.java,
                ArticleDbConstants.DATABASE_NAME
            ).build()
          }
          return INSTANCE as ArticleDatabase
        }
      }
      return INSTANCE as ArticleDatabase
    }
  }
}