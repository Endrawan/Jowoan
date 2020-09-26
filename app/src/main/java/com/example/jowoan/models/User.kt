package com.example.jowoan.models

import com.google.gson.annotations.SerializedName

data class User(
    var ID: Int,
    @SerializedName("fullname") var fullName: String,
    var email: String,
    var password: String,
    var phone: String,
    @SerializedName("avatar_id") var avatarID: Int,
    var avatar: Avatar?,
    @SerializedName("external_type") var externalType: String,
    @SerializedName("external_id") var externalID: String,
    var token: String,
    var points: Int,
    @SerializedName("owned_avatars") var ownedAvatars: List<Avatar>,
    var friends: List<User>
) {
    constructor() : this(
        0, "", "", "", "", 0,
        null, "", "", "", 0, listOf<Avatar>(),
        listOf<User>()
    )
}