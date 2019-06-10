package de.iskae.data.core.topheadlines

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import de.iskae.core.constants.Category
import de.iskae.core.constants.Country
import javax.inject.Inject

class TopHeadlinesPreferencesManager @Inject constructor(private val application: Application) {

  fun setCountryPreference(country: Country) {
    getSharedPreferences().edit().putString(KEY_PREFERENCE_COUNTRY, country.name).apply()
  }

  fun setCategoryPreference(category: Category) {
    getSharedPreferences().edit().putString(KEY_PREFERENCE_CATEGORY, category.name).apply()
  }

  fun getCountryPreference(): String? {
    return getSharedPreferences().getString(KEY_PREFERENCE_COUNTRY, null)
  }

  fun getCategoryPreference(): String? {
    return getSharedPreferences().getString(KEY_PREFERENCE_CATEGORY, null)
  }

  fun getLastRequestedPageNumber(): Int {
    return getSharedPreferences().getInt(KEY_PREFERENCE_LAST_REQUESTED_PAGE_NUMBER, 0)
  }

  fun setLastRequestedPageNumber(pageNumber: Int) {
    getSharedPreferences().edit().putInt(KEY_PREFERENCE_LAST_REQUESTED_PAGE_NUMBER, pageNumber).apply()
  }

  fun clearCountryPreference() {
    getSharedPreferences().edit().remove(KEY_PREFERENCE_COUNTRY).apply()
  }

  fun clearCategoryPreference() {
    getSharedPreferences().edit().remove(KEY_PREFERENCE_CATEGORY).apply()
  }

  private fun getSharedPreferences(): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
  }
}