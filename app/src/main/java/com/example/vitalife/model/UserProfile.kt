package com.example.vitalife.model

data class UserProfile(
    val nombres: String?,
    val apellidos: String?,
    val email: String?,
    val fecha_nacimiento: String?,
    val peso: String?,
    val talla: String?,
    val genero: String?,
    val nivel_actividad: String?,
    val edad: Int? // Edad debe ser Int
)
