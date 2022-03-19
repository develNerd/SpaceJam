package jc.iakakpo.spacejam.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Isaac Akakpo
 * Created on 3/19/2022 9:22 PM
 */
@Singleton
class PrefManager @Inject constructor(@ApplicationContext context: Context) {

  val IS_COMPANY_DETAILS_SAVED = "IS_COMPANY_DETAILS_SAVED"

  private val sharedPreferences:SharedPreferences by lazy {
    PreferenceManager.getDefaultSharedPreferences(context)
  }


  var isCompanyDetailsSaved: Boolean
    set(value) = saveToSharedPref(IS_COMPANY_DETAILS_SAVED,value)
    get() = getSharedPrefBoolean(IS_COMPANY_DETAILS_SAVED)

  private fun saveToSharedPref(key: String, value: Boolean) {
    sharedPreferences.edit(true) {
      putBoolean(key, value)
    }
  }

  private fun getSharedPrefBoolean(key: String): Boolean {
    return sharedPreferences
      .getBoolean(key, false)
  }

}