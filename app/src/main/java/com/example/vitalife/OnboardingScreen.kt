package com.example.vitalife

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.material3.ButtonDefaults // Modificar boton independiente

@Composable
fun OnboardingScreen(navController: NavController) {
    // Fondo de la pantalla
    Surface(
        modifier = Modifier.fillMaxSize()
        // color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen superior
            Image(
                painter = painterResource(id = R.drawable.onboarding_image),
                contentDescription = "Onboarding Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Crop
            )

            // Título
            Text(
                text = "¡Bienvenido a Vitalife!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = "Haz del ejercicio un hábito",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            // Espaciado
            Spacer(modifier = Modifier.height(200.dp))

            // Botón estilizado
            Button(
                onClick = { navController.navigate("register") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50), // Verde claro
                    contentColor = Color.White // Color del texto
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(40.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Empezar", fontSize = 16.sp)
            }
        }
    }
}