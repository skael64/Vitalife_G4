package com.example.vitalife.model
import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("nombres") val nombres: String?,
    @SerializedName("apellidos") val apellidos: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("fecha_nacimiento") val fechaNacimiento: String?,
    @SerializedName("peso") val peso: String?,
    @SerializedName("talla") val talla: String?,
    @SerializedName("genero") val genero: String?,
    @SerializedName("nivel_actividad") val nivelActividad: String?,
    @SerializedName("edad") val edad: Int?
)