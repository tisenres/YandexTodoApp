package com.tisenres.yandextodoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TodoListDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("list")
    val todoList: List<TodoDto>,
    @SerializedName("revision")
    val revision: Int,
)