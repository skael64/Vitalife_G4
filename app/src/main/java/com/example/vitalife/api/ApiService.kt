package com.example.vitalife.api

import com.example.vitalife.model.LoginRequest
import com.example.vitalife.model.LoginResponse
import com.example.vitalife.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("register.php")
    fun registerUser(@Body request: RegisterRequest): Call<ApiResponse>

    // 📌 Endpoint para iniciar sesión
    @Headers("Content-Type: application/json")
    @POST("login.php")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}
