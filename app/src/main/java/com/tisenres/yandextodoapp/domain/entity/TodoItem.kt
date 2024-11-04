package com.tisenres.yandextodoapp.domain.entity

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance = Importance.Normal,
    val deadline: Date?,
    val isCompleted: Boolean = false,
    val createdAt: Date,
    val modifiedAt: Date
)
