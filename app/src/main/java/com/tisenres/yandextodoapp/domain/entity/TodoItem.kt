package com.tisenres.yandextodoapp.domain.entity

import java.util.Date

data class TodoItem(
    val id: String,
    var text: String,
    var importance: Importance,
    var deadline: Date? = null,
    var isCompleted: Boolean,
    val createdAt: Date,
    var modifiedAt: Date? = null
)