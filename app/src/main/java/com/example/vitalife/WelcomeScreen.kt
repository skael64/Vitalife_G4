package com.example.vitalife

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WelcomeScreen(navController: NavController, userName: String?, userId: Int?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido, ${userName ?: "Usuario"}")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (userId != null) {
                navController.navigate("profile/$userId")
            } else {
                navController.navigate("profile/0")
            }
        }) {
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