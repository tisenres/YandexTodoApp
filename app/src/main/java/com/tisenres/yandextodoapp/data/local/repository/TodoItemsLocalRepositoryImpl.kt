package com.tisenres.yandextodoapp.data.local.repository

import com.tisenres.yandextodoapp.data.local.preference.AppPreferences
import com.tisenres.yandextodoapp.domain.repository.RevisionRepository
import javax.inject.Inject

class RevisionRepositoryImpl @Inject constructor(
    private val preferences: AppPreferences
) : RevisionRepository {
    override suspend fun getCurrentRevision(): Int {
        return preferences.getCurrentRevision()
    }

    override suspend fun setCurrentRevision(revision: Int) {
        preferences.setCurrentRevision(revision)
    }
}