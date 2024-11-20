package com.tisenres.yandextodoapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences

private const val CURRENT_REVISION = "current_revision"

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getCurrentRevision(): Int {
        return preferences.getInt(CURRENT_REVISION, 0)
    }

    fun setCurrentRevision(revision: Int) {
        preferences.edit().putInt(CURRENT_REVISION, revision).apply()
    }
}