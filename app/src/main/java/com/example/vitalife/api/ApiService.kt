package com.example.vitalife.api

import com.example.vitalife.model.RegisterRequest
import com.example.vitalife.api.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("register.php")
    fun registerUser(@Body request: RegisterRequest): Call<ApiResponse>
}
