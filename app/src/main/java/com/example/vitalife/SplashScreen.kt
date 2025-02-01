package com.example.vitalife

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Splash Screen - Vitalife")
    }
    // Simula un retraso y navega automáticamente
    android.os.Handler().postDelayed({
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }, 2000)
}