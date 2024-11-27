package com.tisenres.yandextodoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TodoResponseDto(
    @SerializedName("status")
    val status: String?,
    @SerializedName("element")
    val element: TodoDto?,
    @SerializedName("revision")
    val revision: Int
)