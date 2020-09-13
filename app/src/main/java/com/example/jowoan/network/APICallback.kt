package com.example.jowoan.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APICallback<T>(val action: Action<T>) : Callback<APIResponse<T>> {
    private val TAG = "APICallback"

    override fun onResponse(call: Call<APIResponse<T>>, response: Response<APIResponse<T>>) {
        if (response.isSuccessful) {
            Log.d(TAG, "Response status success")
            val data = response.body()?.data
            val message = response.body()?.message
            Log.d(TAG, "responseData:${data.toString()}")
            if (data != null) {
                action.responseSuccess(data)
            } else {
                if (message != null)
                    action.dataNotFound(message)
                else
                    action.dataNotFound("Message is blank")
            }
        } else {
            Log.d(TAG, "Response status failed")
            Log.d(TAG, "errorCode:${response.code()}")
            Log.d(TAG, "errorMessage:${response.message()}")

            val gson = Gson()
            val type = object : TypeToken<APIResponse<T>>() {}.type
            val errorResponse: APIResponse<T>? =
                gson.fromJson(response.errorBody()?.charStream(), type)
            Log.d(TAG, "errorMessage:${errorResponse.toString()}")

            val status = "not found"
            val message = "not found"
            if (errorResponse != null) {
                if (errorResponse.message != null) {
                    action.responseFailed(errorResponse.status, errorResponse.message)
                } else {
                    action.responseFailed(errorResponse.status, message)
                }
            } else {
                action.responseFailed(status, message)
            }
        }
    }

    override fun onFailure(call: Call<APIResponse<T>>, t: Throwable) {
        Log.d(TAG, "Network Failed")
        Log.d(TAG, "errorMessage:${t.message}")
        action.networkFailed(t)
    }

    interface Action<T> {
        fun responseSuccess(data: T)
        fun dataNotFound(message: String)
        fun responseFailed(status: String, message: String)
        fun networkFailed(t: Throwable)
    }
}