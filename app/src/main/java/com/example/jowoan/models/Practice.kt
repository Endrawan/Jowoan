package com.example.jowoan.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Practice(
    val ID: Int,
    @SerializedName("CreatedAt") val createdAt: Date,
    @SerializedName("UpdatedAt") val updatedAt: Date,
    @SerializedName("DeletedAt") val deletedAt: Date,
    val name: String,
    val image: String,
    val subpractices: List<Subpractice>
)