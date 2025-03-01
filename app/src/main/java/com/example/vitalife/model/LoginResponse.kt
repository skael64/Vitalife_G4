package com.example.vitalife.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: Int? = null,
    val nombres: String? = null,
    val apellidos: String? = null
)