package com.example.vitalife.model

data class RegisterRequest(
    val nombres: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val fecha_nacimiento: String?, // Puede ser null
    val peso: Double?,
    val talla: Double?
)
