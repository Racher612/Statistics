package com.project.statistics.monitoring.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class SharedPrefsManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "PinnedCities")

    private val sharedPrefs = context.getSharedPreferences("app_launches", Context.MODE_PRIVATE)

    fun updatePackage(
        packageName : String
    ){
        val launchCount = sharedPrefs.getInt(packageName, 0)
        sharedPrefs.edit().putInt(packageName, launchCount + 1).apply()
    }

    fun getPackageCount(
        packageName: String
    ): Int {
        return sharedPrefs.getInt(packageName, 0)
    }
}