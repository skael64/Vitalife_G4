package com.example.vitalife

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vitalife.model.UserProfile
import com.example.vitalife.utils.SharedHelper

@Composable
fun WelcomeScreen(navController: NavController, userName: String?, userId: Int?) {
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    // Obtener el contexto
    val context = LocalContext.current
    // Instancia de SharedPreferencesHelper
    val sharedPreferencesHelper = remember { SharedHelper(context) }
    // Obtener el userId desde SharedPreferences
    val userId = sharedPreferencesHelper.getUserId()
    if (userId != null) {
        fetchUserProfile(userId) { profile ->
            userProfile = profile
        }
    }
    Column(
        modifier = Modifier.padding(20.dp).fillMaxSize(),

        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            Text(text = "Bienvenido, ${userProfile?.nombres ?: "Usuario"}", fontSize = 22.sp , fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text(text = "Ya está todo listo, alcancemos juntos tus objetivos")
        }

        Button(onClick = {
            navController.navigate("profile")
        },
                modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ir al Inicio")
        }
    }
}

@Preview
@Composable
fun VistaPreviaWelcomeScreen() {
    val navController = rememberNavController()
    WelcomeScreen(
        navController = navController,
        userName = "Juan",  // Valor de ejemplo para el nombre de usuario
        userId = 123        // Valor de ejemplo para el ID de usuario
    )
}