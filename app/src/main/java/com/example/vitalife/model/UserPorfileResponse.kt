package com.example.vitalife.model

data class UserProfileResponse(
    val success: Boolean,
    val user: UserProfile
)

data class UserProfile(
    val nombres: String,
    val apellidos: String,
    val email: String,
    val fecha_nacimiento: String?,
    val peso: Float?,
    val talla: Float?,
    val genero: String?,
    val nivelActividad: String?,
    val edad: Int?
)