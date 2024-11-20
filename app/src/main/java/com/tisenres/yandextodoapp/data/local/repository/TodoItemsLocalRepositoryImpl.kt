package com.tisenres.yandextodoapp.data.local.repository

import com.tisenres.yandextodoapp.data.local.preference.AppPreferences
import com.tisenres.yandextodoapp.domain.repository.TodoItemsLocalRepository
import javax.inject.Inject

class TodoItemsLocalRepositoryImpl @Inject constructor(
    private val preference: AppPreferences
) : TodoItemsLocalRepository {
    override suspend fun getCurrentRevision(): Int {
        return preference.getCurrentRevision()
    }

    override suspend fun setCurrentRevision(revision: Int) {
        preference.setCurrentRevision(revision)
    }
}