package com.example.vitalife

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vitalife.api.RetrofitClient
import com.example.vitalife.api.ApiResponse
import com.example.vitalife.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.navigation.compose.rememberNavController

@Composable
fun RegisterScreen(navController: NavController) {
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("Male") }
    var nivelActividad by remember { mutableStateOf("Sedentario") }
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
        InputField(password, { password = it }, "Contraseña", KeyboardType.Password, isPassword = true)
        InputField(fechaNacimiento, { fechaNacimiento = it }, "Fecha de Nacimiento (DD/MM/YYYY)")
        InputField(peso, { peso = it }, "Peso (kg)", KeyboardType.Number)
        InputField(talla, { talla = it }, "Talla (m)", KeyboardType.Number)

        DropdownField(
            label = "Género",
            selectedValue = genero,
            options = listOf("Male", "Female", "Other"),
            onValueChange = { genero = it }
        )

        DropdownField(
            label = "Nivel de Actividad",
            selectedValue = nivelActividad,
            options = listOf("Sedentario", "Moderado", "Activo", "Muy Activo"),
            onValueChange = { nivelActividad = it }
        )

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
                        registerUser(
                            nombres, apellidos, email, password, fechaNacimiento, peso, talla, genero, nivelActividad,
                            navController, context
                        )
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

// 📌 Componente de Entrada de Texto con Soporte para Contraseñas
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
    Spacer(modifier = Modifier.height(8.dp))
}

// 📌 Dropdown para Género y Nivel de Actividad
@Composable
fun DropdownField(label: String, selectedValue: String, options: List<String>, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label)
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(text = selectedValue)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(text = { Text(option) }, onClick = {
                        onValueChange(option)
                        expanded = false
                    })
                }
            }
        }
    }
}

// 📌 Función para Registrar Usuario con Retrofit
fun registerUser(
    nombres: String, apellidos: String, email: String, password: String,
    fechaNacimiento: String?, peso: String?, talla: String?, genero: String, nivelActividad: String,
    navController: NavController, context: android.content.Context
) {
    val pesoDouble = peso?.toDoubleOrNull()
    val tallaDouble = talla?.toDoubleOrNull()

    val formattedFechaNacimiento = try {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fechaNacimiento!!)
            ?.let { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it) }
    } catch (e: Exception) {
        null
    }

    val request = RegisterRequest(nombres, apellidos, email, password, formattedFechaNacimiento, pesoDouble, tallaDouble, genero, nivelActividad)

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

@Preview
@Composable
fun VistaPreviaRegisterScreen() {
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}