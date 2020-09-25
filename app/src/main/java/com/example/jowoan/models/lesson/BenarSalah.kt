package com.example.jowoan.models.lesson

data class BenarSalah(
    val ID: Int,
    val title: String,
    val statement: String,
    val question: String,
    val answer: Boolean,
    val correction: String
)