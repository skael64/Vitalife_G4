package com.example.vitalife

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vitalife.api.RetrofitClient
import com.example.vitalife.model.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavController, userId: Int) {
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(userId) {
        Log.d("ProfileDebug", "Obteniendo perfil para userId: $userId")
        fetchUserProfile(userId) { profile ->
            userProfile = profile
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF4CAF50), Color(0xFFB2FF59))))
    ) {
        when {
            isLoading -> {
                Text(text = "Cargando perfil...", modifier = Modifier.align(Alignment.Center))
            }
            userProfile == null -> {
                Text(text = "Error al cargar el perfil", modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                val user = userProfile!!

                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Botón de regresar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nombre del usuario
                    Text(
                        text = "${user.nombres ?: "Usuario"} ${user.apellidos ?: "No registrado"}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user.nivel_actividad ?: "No especificado",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información clave (Talla, Peso, Edad)
                    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                        InfoCard("Talla", "${user.talla ?: "No registrada"} cm")
                        InfoCard("Peso", "${user.peso ?: "No registrado"} kg")
                        InfoCard("Edad", "${user.edad ?: "N/A"} años")
                    }
                }
            }
        }
    }
}

// 📌 Tarjeta de información clave (Talla, Peso, Edad)
@Composable
fun InfoCard(title: String, value: String) {
    Column(
        modifier = Modifier
            .size(100.dp, 60.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = title, fontSize = 12.sp, color = Color.Gray)
    }
}

// 📌 Secciones con opciones
@Composable
fun SectionCard(title: String, items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF388E3C))
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { item ->
            Text(
                text = item,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Acción al hacer click */ }
                    .padding(vertical = 8.dp)
            )
        }
    }
}

// 📌 Función para obtener los datos del usuario desde la API
fun fetchUserProfile(userId: Int, onResult: (UserProfile?) -> Unit) {
    val call = RetrofitClient.instance.getUserProfile(userId)

    call.enqueue(object : Callback<UserProfile> {
        override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
            Log.d("ProfileDebug", "Código HTTP: ${response.code()}") // 📌 Ver código de respuesta

            if (response.isSuccessful) {
                val userProfile = response.body()
                Log.d("ProfileDebug", "JSON recibido: ${response.body()}") // 🛠️ Nuevo Log con JSON
                onResult(userProfile)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ProfileError", "Error al obtener perfil: $errorBody") // 📌 Ver error
                onResult(null)
            }
        }

        override fun onFailure(call: Call<UserProfile>, t: Throwable) {
            Log.e("ProfileError", "Error de conexión: ${t.message}") // 📌 Ver error de conexión
            onResult(null)
        }
    })
}
