package com.tisenres.yandextodoapp.presentation.main

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tisenres.yandextodoapp.domain.usecases.GetTodosUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PeriodicSyncWorker @Inject constructor(
    @ApplicationContext context: Context,
    workerParams: WorkerParameters,
    private val getTodosUseCase: GetTodosUseCase
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            getTodosUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
