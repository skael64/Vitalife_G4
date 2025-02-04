package com.example.vitalife.model

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("fecha_nacimiento") val fechaNacimiento: String,
    @SerializedName("peso") val peso: String,
    @SerializedName("talla") val talla: String
)
