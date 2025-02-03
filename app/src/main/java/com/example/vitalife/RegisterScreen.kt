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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.vitalife.model.RegisterRequest

@Composable
fun RegisterScreen(navController: NavController) {
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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

        // NOMBRE
        OutlinedTextField(
            value = nombres,
            onValueChange = { nombres = it },
            label = { Text("Nombres") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // APELLIDO
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // CONTRASEÑA
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CHECKBOX
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = acceptedTerms,
                onCheckedChange = { acceptedTerms = it }
            )
            Text(text = "Al continuar aceptas nuestra política de privacidad y términos de uso")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Registro
        Button(
            onClick = {
                if (acceptedTerms) {
                    registerUser(nombres, apellidos, email, password, navController, context)
                } else {
                    Toast.makeText(context, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50), // Verde claro
                contentColor = Color.White // Color del texto
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

fun registerUser(nombres: String, apellidos: String, email: String, password: String, navController: NavController, context: android.content.Context) {
    val request = RegisterRequest(nombres, apellidos, email, password)
    val call = RetrofitClient.instance.registerUser(request)

    call.enqueue(object : Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    if (it.success) {
                        navController.navigate("profile") // Mueve a la pantalla de perfil si se registra
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
