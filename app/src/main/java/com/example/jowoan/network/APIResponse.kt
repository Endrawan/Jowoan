package com.example.jowoan.network

data class APIResponse<T>(
    val data: T?,
    val message: String?,
    val status: String
)