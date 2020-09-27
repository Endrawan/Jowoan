package com.example.jowoan.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Activity(
    @SerializedName("subpractice_id") val subpracticeID: Int,
    @SerializedName("user_id") val userID: Int,
    @SerializedName("points_got") val pointsGot: Int,
    @SerializedName("CreatedAt") val createdAt: Date?,
    @SerializedName("UpdatedAt") val updatedAt: Date?,
    @SerializedName("DeletedAt") val deletedAt: Date?
)