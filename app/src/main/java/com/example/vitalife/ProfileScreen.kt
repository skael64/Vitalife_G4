package com.example.vitalife

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vitalife.api.RetrofitClient
import com.example.vitalife.model.UserProfile
import com.example.vitalife.model.UserProfileResponse
import androidx.navigation.compose.rememberNavController
import com.example.vitalife.utils.SharedHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavController) {
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var notificationEnabled by remember { mutableStateOf(true) }
    // Obtener el contexto
    val context = LocalContext.current

    // Instancia de SharedPreferencesHelper
    val sharedPreferencesHelper = remember { SharedHelper(context) }

    // Obtener el userId desde SharedPreferences
    val userId = sharedPreferencesHelper.getUserId()

    LaunchedEffect(userId) {
        if (userId != null) {
            Log.d("ProfileDebug", "Obteniendo perfil para userId: $userId")

            fetchUserProfile(userId!!) { profile ->
                userProfile = profile
                isLoading = false
            }
        } else {
            Log.e("ProfileError", "userId es nulo")
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
                    modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(
                        rememberScrollState()
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /*
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }*/

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 Datos del usuario
                    Text(
                        text = "${user.nombres ?: "Usuario"} ${user.apellidos ?: ""}".trim(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user.nivelActividad ?: "No especificado",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 Información clave (Talla, Peso, Edad)
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        InfoCard("Talla", "${user.talla ?: "No registrada"} cm")
                        InfoCard("Peso", "${user.peso ?: "No registrado"} kg")
                        InfoCard("Edad", "${user.edad ?: "N/A"} años")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 🔹 Sección Cuenta
                    SectionCard(
                        title = "Cuenta",
                        items = listOf("Datos Personales", "Logros", "Historial de Actividades", "Progreso de Entrenamiento"),
                        navController = navController
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 Sección Notificación
                    NotificationToggle(
                        title = "Notificación emergente",
                        checked = notificationEnabled,
                        onCheckedChange = { notificationEnabled = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 Sección Otros
                    SectionCard(
                        title = "Otros",
                        items = listOf("Contáctanos", "Política de privacidad", "Ajustes"),
                        navController = navController
                    )
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
fun SectionCard(title: String, items: List<String>, navController: NavController) {
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
                    .clickable {
                        when (item) {
                            "Historial de Actividades" -> navController.navigate("entrenamiento")
                            "Progreso de Entrenamiento" -> navController.navigate("workoutTracker") // Navegar al progreso de entrenamiento
                        }
                    }
                    .padding(vertical = 8.dp)
            )
        }
    }
}

// 📌 Toggle de notificaciones
@Composable
fun NotificationToggle(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4CAF50), shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

// 📌 Función para obtener los datos del usuario desde la API
fun fetchUserProfile(userId: Int, onResult: (UserProfile?) -> Unit) {
    val call = RetrofitClient.instance.getUserProfile(userId)

    call.enqueue(object : Callback<UserProfileResponse> {
        override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
            Log.d("ProfileDebug", "Código HTTP: ${response.code()}")
            Log.d("ProfileDebug", "Cuerpo crudo de la respuesta: ${response.body()}")

            if (response.isSuccessful) {
                val userResponse = response.body()
                val userProfile = userResponse?.user // 📌 Extrae `user`
                Log.d("ProfileDebug", "Usuario cargado correctamente: $userProfile")

                onResult(userProfile)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ProfileError", "Error en la respuesta: $errorBody")
                onResult(null)
            }
        }

        override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
            Log.e("ProfileError", "Error de conexión: ${t.message}")
            onResult(null)
        }
    })
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaProfileScreen() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
