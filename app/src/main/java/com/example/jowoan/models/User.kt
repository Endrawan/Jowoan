package com.example.jowoan.models

data class User(
    var ID: Int,
    var fullName: String,
    var email: String,
    var password: String,
    var phone: String,
    var avatarID: Int,
    var avatar: Avatar?,
    var externalType: String,
    var externalID: String
) {
    constructor() : this(0, "", "", "", "", 0, null, "", "")
}