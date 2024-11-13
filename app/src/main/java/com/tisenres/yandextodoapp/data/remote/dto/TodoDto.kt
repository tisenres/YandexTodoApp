package com.tisenres.yandextodoapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TodoDto(
    @SerializedName("id")
    val id: UUID,
    @SerializedName("text")
    val text: String,
    @SerializedName("importance")
    val deadline: Int,
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("color")
    val color: String?,
    @SerializedName("deadline")
    val importance: String,
    @SerializedName("created_at")
    val createdAt: Int,
    @SerializedName("changed_at")
    val changedAt: Int,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: String,
)