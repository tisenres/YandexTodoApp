package com.tisenres.yandextodoapp.domain.repository

interface RevisionRepository {
    suspend fun getCurrentRevision(): Int
    suspend fun setCurrentRevision(revision: Int)
}