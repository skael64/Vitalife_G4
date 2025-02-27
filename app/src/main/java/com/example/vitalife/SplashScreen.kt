package com.example.vitalife

import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Imagen superior
        Image(
            painter = painterResource(id = R.drawable.onboarding_image),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .size(400.dp)
                .padding(bottom = 32.dp),
            contentScale = ContentScale.Crop
        )
    }
    android.os.Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }, 2000)

}

