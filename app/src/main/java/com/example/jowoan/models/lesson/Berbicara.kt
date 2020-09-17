package com.example.jowoan.models.lesson

import com.google.gson.annotations.SerializedName

data class Berbicara(
    val ID: Int,
    val title: String,
    @SerializedName("jowo_lang") val jowoLang: String,
    @SerializedName("indo_lang") val indoLang: String
)