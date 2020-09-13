package com.example.jowoan.models

import com.google.gson.annotations.SerializedName

data class User(
    var ID: Int,
    @SerializedName("fullname") var fullName: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("avatar_id") var avatarID: Int,
    @SerializedName("avatar") var avatar: Avatar?,
    @SerializedName("external_type") var externalType: String,
    @SerializedName("external_id") var externalID: String,
    @SerializedName("token") var token: String
) {
    constructor() : this(0, "", "", "", "", 0, null, "", "", "")
}