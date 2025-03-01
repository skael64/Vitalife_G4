package com.example.vitalife.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedHelper(context: Context) {
    // Nombre del archivo de SharedPreferences
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Guardar el userId
    fun saveUserId(userId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("userId", userId)
        editor.apply()
        Log.d("SharedPreferences", "userId guardado: $userId")
    }

    // Recuperar el userId
    fun getUserId(): Int? {
        val userId = sharedPreferences.getInt("userId", -1) // -1 es un valor por defecto si no existe
        return if (userId != -1) userId else null
    }

    // Eliminar el userId (por ejemplo, al cerrar sesión)
    fun clearUserId() {
        val editor = sharedPreferences.edit()
        editor.remove("userId")
        editor.apply()
        Log.d("SharedPreferences", "userId eliminado")
    }
}