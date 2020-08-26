package com.example.jowoan.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {

    val API_URL = "http://jowoan.herokuapp.com/"

    fun create(): JowoanService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
        return retrofit.create(JowoanService::class.java)
    }
}