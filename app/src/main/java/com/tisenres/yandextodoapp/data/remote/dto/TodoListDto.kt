package com.tisenres.yandextodoapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TodoListDto(
    @SerializedName("status")
    val status: String,

    @SerializedName("list")
    val todoList: List<TodoDto>,

    @SerializedName("revision")
    val revision: Int,
)

data class TodoDto(
    @SerializedName("id")
    val id: UUID,

    @SerializedName("text")
    val text: String,

    @SerializedName("deadline")
    val deadline: Int,

    @SerializedName("done")
    val done: Boolean,

    @SerializedName("color")
    val color: String?,

    @SerializedName("created_at")
    val createdAt: Int,

    @SerializedName("changed_at")
    val changedAt: Int,

    @SerializedName("last_updated_by")
    val lastUpdatedBy: Int,
)