package com.example.vitalife.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: Int? = null,
    val fullName: String? = null // 🔹 Agregar el nombre completo del usuario
)
