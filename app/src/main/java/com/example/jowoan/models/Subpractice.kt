package com.example.jowoan.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Subpractice(
    val ID: Int,
    @SerializedName("CreatedAt") val createdAt: Date,
    @SerializedName("UpdatedAt") val updatedAt: Date,
    @SerializedName("DeletedAt") val deletedAt: Date,
    val title: String,
    val name: String,
    @SerializedName("practice_id") val practiceID: Int
)