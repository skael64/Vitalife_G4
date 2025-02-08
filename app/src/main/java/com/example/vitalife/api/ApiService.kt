package com.example.vitalife.api

import com.example.vitalife.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    // 📌 Registro de usuario
    @Headers("Content-Type: application/json")
    @POST("register.php")
    fun registerUser(@Body request: RegisterRequest): Call<ApiResponse>

    // 📌 Inicio de sesión
    @Headers("Content-Type: application/json")
    @POST("login.php")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    // 📌 Obtener perfil del usuario por ID
    @GET("getUserProfile.php")
    fun getUserProfile(@Query("userId") userId: Int): Call<UserProfile>


}
