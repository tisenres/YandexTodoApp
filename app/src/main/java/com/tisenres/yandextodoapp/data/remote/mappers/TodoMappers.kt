package com.tisenres.yandextodoapp.data.remote.mappers

import com.tisenres.yandextodoapp.data.remote.dto.TodoDto
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import java.util.Date
import java.util.UUID

fun TodoDto.toDomainModel(): TodoItem {
    return TodoItem(
        id = id.toString(),
        text = text,
        importance = mapColorToImportance(color),
        isCompleted = done,
        createdAt = Date(createdAt),
        modifiedAt = Date(changedAt)
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
        lastUpdatedBy = "pixel 4"
    )
}

private fun mapColorToImportance(color: String?): Importance {
    return when (color?.lowercase()) {
        "red" -> Importance.HIGH
        "yellow" -> Importance.NORMAL
        "green" -> Importance.LOW
        else -> Importance.NORMAL
    }
}