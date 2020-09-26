package com.example.jowoan.models

import com.google.gson.annotations.SerializedName

data class Completion(
    @SerializedName("total_correct") val totalCorrect: Int,
    @SerializedName("expected_correct") val expectedCorrect: Int,
    @SerializedName("subpractice_id") val subpracticeID: Int,
    @SerializedName("user_id") val userID: Int
)