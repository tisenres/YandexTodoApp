package com.tisenres.yandextodoapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

private const val CURRENT_REVISION = "current_revision"

class AppPreferences @Inject constructor(
    private val context: Context
) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    suspend fun getCurrentRevision(): Int {
        return preferences.getInt(CURRENT_REVISION, 0)
    }

    suspend fun setCurrentRevision(revision: Int) {
        preferences.edit().putInt(CURRENT_REVISION, revision).apply()
    }
}