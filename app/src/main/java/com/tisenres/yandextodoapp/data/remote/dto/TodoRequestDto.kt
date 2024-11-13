package com.tisenres.yandextodoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TodoRequestDto(
    @SerializedName("element")
    val element: TodoDto,
)