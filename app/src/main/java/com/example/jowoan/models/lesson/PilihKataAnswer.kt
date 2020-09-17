package com.example.jowoan.models.lesson

import com.google.gson.annotations.SerializedName

data class PilihKataAnswer(
    val ID: Int,
    val answer: String,
    @SerializedName("pilih_kata_id") val pilihKataId: Int
)