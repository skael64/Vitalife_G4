package com.example.vitalife.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: Int?,
    val nombres: String?,  // 🔹 Agregamos nombres
    val apellidos: String? // 🔹 Agregamos apellidos
)