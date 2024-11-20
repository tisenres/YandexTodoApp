package com.tisenres.yandextodoapp.domain.repository

interface TodoItemsLocalRepository {
    suspend fun getCurrentRevision(): Int
    suspend fun setCurrentRevision(revision: Int)
}