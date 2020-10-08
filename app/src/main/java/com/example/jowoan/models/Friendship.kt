package com.example.jowoan.models

import com.google.gson.annotations.SerializedName

data class Friendship(
    @SerializedName("user_id") val userID: Int,
    @SerializedName("friend_id") val friendID: Int,
//    @SerializedName("status_id") val statusID: Int,
    val result: String
)