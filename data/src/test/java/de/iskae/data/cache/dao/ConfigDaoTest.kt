package de.iskae.data.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import de.iskae.data.factory.ArticleFactory
import de.iskae.data.factory.ArticleFactory.makeConfig
import de.iskae.data.repository.cache.db.ArticleDatabase
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val database = Room.inMemoryDatabaseBuilder(
      RuntimeEnvironment.application.applicationContext,
      ArticleDatabase::class.java)
      .allowMainThreadQueries()
      .build()

  @After
  fun closeDb() {
    database.close()
  }

  @Test
  fun saveConfigSuccessful() {
    val config = makeConfig()
    database.configDao().insertConfig(config)

    val testObserver = database.configDao().getConfig(Country.DE.name,Category.BUSINESS.name).test()
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)
    testObserver.assertValue(config)
  }

}