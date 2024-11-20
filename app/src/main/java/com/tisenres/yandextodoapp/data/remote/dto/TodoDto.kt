package com.tisenres.yandextodoapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TodoDto(
    @SerializedName("id")
    val id: UUID,
    @SerializedName("text")
    val text: String,
    @SerializedName("importance")
    val importance: String,
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("color")
    val color: String?,
    @SerializedName("deadline")
    val deadline: Long?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("changed_at")
    val changedAt: Long,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: String,
)