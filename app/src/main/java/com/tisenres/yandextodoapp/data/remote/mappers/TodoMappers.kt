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
        deadline = Date(deadline * 1000L),
        isCompleted = done,
        createdAt = Date(createdAt * 1000L),
        modifiedAt = Date(changedAt * 1000L)
    )
}

fun TodoItem.toNetworkModel(): TodoDto {
    return TodoDto(
        id = UUID.fromString(id),
        text = text,
        importance = "low",
        deadline = deadline?.time?.div(1000)?.toInt() ?: 0,
        done = isCompleted,
        color = null,
        createdAt = (createdAt.time / 1000).toInt(),
        changedAt = modifiedAt?.time?.div(1000)?.toInt() ?: 0,
        lastUpdatedBy = "device123"
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