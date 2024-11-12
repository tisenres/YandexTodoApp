package com.tisenres.yandextodoapp.data.remote.mappers

import com.tisenres.yandextodoapp.data.remote.dto.TodoDto
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import java.util.Date

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

private fun mapColorToImportance(color: String?): Importance {
    return when (color?.lowercase()) {
        "red" -> Importance.HIGH
        "yellow" -> Importance.NORMAL
        "green" -> Importance.LOW
        else -> Importance.NORMAL
    }
}