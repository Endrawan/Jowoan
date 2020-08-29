package com.example.jowoan.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {

    val API_URL = "http://jowoan.herokuapp.com/"
    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    fun create(): JowoanService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(API_URL)
            .build()
        return retrofit.create(JowoanService::class.java)
    }
}