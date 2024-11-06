package com.tisenres.yandextodoapp.domain.entity

import java.time.LocalDateTime

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance = Importance.Normal,
    val deadline: LocalDateTime? = null, // Optional with a default value of null
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(), // Default to current time
    val modifiedAt: LocalDateTime = LocalDateTime.now() // Default to current time
)

