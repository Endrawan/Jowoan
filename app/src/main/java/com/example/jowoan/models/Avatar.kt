package com.example.jowoan.models

import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("id") val ID: Int,
    @SerializedName("url") val URL: String
)