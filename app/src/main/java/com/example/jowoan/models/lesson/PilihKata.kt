package com.example.jowoan.models.lesson

import com.google.gson.annotations.SerializedName

data class PilihKata(
    val ID: Int,
    @SerializedName("char_length") val charLength: Int,
    @SerializedName("char_start") val charStart: Int,
    val image: String,
    val answers: List<PilihKataAnswer>,
    val question: String,
    val title: String,
    val correction: String
)