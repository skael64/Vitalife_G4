package com.example.vitalife

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vitalife.api.RetrofitClient
import com.example.vitalife.model.LoginRequest
import com.example.vitalife.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.vitalife.ui.theme.components.InputField
import android.content.Context
import android.util.Log

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(email, { email = it }, "Correo Electrónico", KeyboardType.Email)
        InputField(password, { password = it }, "Contraseña", KeyboardType.Password, isPassword = true)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginUser(email, password, navController, context)
                } else {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ingresar")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "¿No tienes cuenta? Regístrate",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { navController.navigate("register") }
        )
    }
}

// 📌 Función para Iniciar Sesión con Retrofit
fun loginUser(email: String, password: String, navController: NavController, context: Context) {
    val request = LoginRequest(email, password)
    val call = RetrofitClient.instance.loginUser(request)

    call.enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    Log.d("LoginDebug", "Respuesta del servidor: $loginResponse")

                    Toast.makeText(context, loginResponse.message, Toast.LENGTH_LONG).show()

                    if (loginResponse.success) {
                        // ✅ Asegurar que obtenemos correctamente el nombre del usuario
                        val nombres = loginResponse.nombres ?: "Usuario"
                        val apellidos = loginResponse.apellidos ?: ""
                        val fullName = "$nombres $apellidos".trim()
                        val userId = loginResponse.userId ?: 0

                        // ✅ Navegación corregida a WelcomeScreen con nombre e ID del usuario
                        navController.navigate("welcome/$fullName/$userId") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            } else {
                Log.e("LoginDebug", "Error en el servidor: ${response.errorBody()?.string()}")
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            Log.e("LoginDebug", "Error en la conexión: ${t.message}")
            Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
        }
    })
}
