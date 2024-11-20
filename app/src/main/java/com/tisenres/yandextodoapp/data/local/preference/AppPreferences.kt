package com.tisenres.yandextodoapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val CURRENT_REVISION = "current_revision"

class AppPreferences @Inject constructor(
    context: Context
) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    suspend fun getCurrentRevision(): Int = withContext(Dispatchers.IO) {
        preferences.getInt(CURRENT_REVISION, 0)
    }

    suspend fun setCurrentRevision(revision: Int) = withContext(Dispatchers.IO) {
        preferences.edit().putInt(CURRENT_REVISION, revision).apply()
    }
}