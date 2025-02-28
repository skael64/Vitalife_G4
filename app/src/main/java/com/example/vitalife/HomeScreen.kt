package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.draw.clip


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("¡Hola, Raúl!", fontSize = 18.sp, color = Color.Gray)
                Text("Tu progreso de hoy", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = { navController.navigate("notifications") }) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones")
            }
            Button(onClick = { navController.navigate("chatbot") }) {
                Text("Ir al Chatbot")
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Summary Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FitnessCard(title = "Pasos", value = "7,842", unit = "Hoy", modifier = Modifier.weight(1f))
            FitnessCard(title = "Calorías", value = "524", unit = "kcal", modifier = Modifier.weight(1f))
            FitnessCard(title = "Ejercicios", value = "3", unit = "Completados", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Training Section
        Text("Entrenamiento Diario", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF90EE90), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("¡Prepárate para el entrenamiento!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Acción de entrenamiento */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("INICIAR", color = Color(0xFF90EE90))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weekly Progress Section
        Text("Progreso Semanal", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            ProgressRow("Lunes", 0.8f, Color(0xFF90EE90))
            ProgressRow("Martes", 0.6f, Color(0xFF87CEFA))
            ProgressRow("Miércoles", 0.4f, Color(0xFFFFA07A))
            ProgressRow("Jueves", 0.7f, Color(0xFF90EE90))
            ProgressRow("Viernes", 0.9f, Color(0xFF87CEFA))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation to Dashboard
        Button(
            onClick = { navController.navigate("dashboard") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEFA))
        ) {
            Text("Ver Dashboard Completo", color = Color.White)
        }
    }
}

@Composable
fun FitnessCard(title: String, value: String, unit: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 16.sp, color = Color.Gray)
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(unit, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun ProgressRow(day: String, progress: Float, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(day, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = progress,
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun VistaPreviaHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
