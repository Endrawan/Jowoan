package com.example.jowoan.models.lesson

import com.google.gson.annotations.SerializedName

data class Hafalan(
    val ID: Int,
    val title: String,
    val image: String,
    @SerializedName("indo_lang") val indoLang: String,
    @SerializedName("jowo_lang") val jowoLang: String
)