package com.tisenres.yandextodoapp.data.remote.mappers

import android.os.Build
import com.tisenres.yandextodoapp.data.remote.dto.TodoDto
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import java.util.Date
import java.util.UUID

fun TodoDto.toDomainModel(): TodoItem {
    return TodoItem(
        id = id.toString(),
        text = text ?: "",
        importance = when (
            importance?.lowercase()
        ) {
            "important" -> Importance.HIGH
            "basic" -> Importance.NORMAL
            "low" -> Importance.LOW
            else -> Importance.NORMAL
        },
        isCompleted = done ?: false,
        deadline = deadline?.let { Date(it) },
        createdAt = Date(createdAt ?: 0),
        modifiedAt = Date(changedAt ?: 0)
    )
}

fun TodoItem.toNetworkModel(): TodoDto {
    return TodoDto(
        id = UUID.fromString(id),
        text = text,
        importance = when (importance) {
            Importance.HIGH -> "important"
            Importance.NORMAL -> "basic"
            Importance.LOW -> "low"
        },
        deadline = deadline?.time,
        done = isCompleted,
        color = null,
        createdAt = createdAt.time,
        changedAt = System.currentTimeMillis(),
        lastUpdatedBy = Build.MODEL
    )
}