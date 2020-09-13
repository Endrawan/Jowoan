package com.example.jowoan.network

import com.example.jowoan.models.Avatar
import com.example.jowoan.models.Practice
import com.example.jowoan.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface JowoanService {
    @GET("avatar")
    fun listAvatars(): Call<List<Avatar>>

    @POST("user/emailSignUp")
    fun emailSignUp(@Body user: User): Call<APIResponse<User>>

    @POST("user/emailSignIn")
    fun emailSignIn(@Body user: User): Call<APIResponse<User>>

    @POST("user/tokenSignUp")
    fun tokenSignUp(@Body user: User): Call<APIResponse<User>>

    @POST("user/tokenSignIn")
    fun tokenSignIn(@Body user: User): Call<APIResponse<User>>

    @GET("practice")
    fun practiceGetAll(@Header("Token") token: String): Call<APIResponse<List<Practice>>>
}