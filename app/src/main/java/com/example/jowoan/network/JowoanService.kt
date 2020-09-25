package com.example.jowoan.network

import com.example.jowoan.models.Avatar
import com.example.jowoan.models.Practice
import com.example.jowoan.models.User
import com.example.jowoan.models.lesson.Lesson
import retrofit2.Call
import retrofit2.http.*

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

    @PUT("user/{id}")
    fun userUpdate(
        @Header("Token") token: String,
        @Path("id") id: Int,
        @Body user: User
    ): Call<APIResponse<User>>

    @GET("lesson")
    fun lessonGet(
        @Header("Token") token: String,
        @Query("subpractice_id") subpracticeID: String
    )
            : Call<APIResponse<List<Lesson>>>
}