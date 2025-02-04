package com.example.vitalife.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vitalife.api.RetrofitClient
import com.example.vitalife.api.ApiResponse
import com.example.vitalife.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(navController: NavController) {
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crea tu cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(nombres, { nombres = it }, "Nombres")
        InputField(apellidos, { apellidos = it }, "Apellidos")
        InputField(email, { email = it }, "Correo Electrónico", KeyboardType.Email)
        InputField(password, { password = it }, "Contraseña", KeyboardType.Password)
        InputField(fechaNacimiento, { fechaNacimiento = it }, "Fecha de Nacimiento (YYYY-MM-DD)")
        InputField(peso, { peso = it }, "Peso (kg)", KeyboardType.Number)
        InputField(talla, { talla = it }, "Talla (m)", KeyboardType.Number)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = acceptedTerms,
                onCheckedChange = { acceptedTerms = it }
            )
            Text(text = "Acepto los términos y condiciones")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nombres.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    if (acceptedTerms) {
                        registerUser(nombres, apellidos, email, password, fechaNacimiento, peso, talla, navController, context)
                    } else {
                        Toast.makeText(context, "Debes aceptar los términos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ),
            enabled = acceptedTerms,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrarse")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "¿Ya tienes cuenta? Inicia sesión",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { navController.navigate("login") }
        )
    }
}

// 📌 Componente de Entrada de Texto Reutilizable
@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(8.dp))
}

// 📌 Función para Registrar Usuario con Retrofit
fun registerUser(
    nombres: String, apellidos: String, email: String, password: String,
    fechaNacimiento: String?, peso: String?, talla: String?,
    navController: NavController, context: android.content.Context
) {
    val pesoDouble = peso?.toDoubleOrNull()
    val tallaDouble = talla?.toDoubleOrNull()
    val request = RegisterRequest(nombres, apellidos, email, password, fechaNacimiento, pesoDouble, tallaDouble)

    val call = RetrofitClient.instance.registerUser(request)

    call.enqueue(object : Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    if (it.success) {
                        navController.navigate("profile")
                    }
                }
            } else {
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
        }
    })
}
