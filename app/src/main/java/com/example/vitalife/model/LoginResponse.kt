package com.example.vitalife.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: Int?,
    val nombres: String?,   // ✅ Se asegura de coincidir con la API
    val apellidos: String?  // ✅ Se asegura de coincidir con la API
)
