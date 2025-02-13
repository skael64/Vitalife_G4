package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WorkoutTrackerScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Encabezado con botón de regreso
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "< Volver",
                fontSize = 18.sp,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Seguimiento del entrenamiento",
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Gráfico de entrenamiento
        TrainingChart()
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Botón de horario de entrenamiento
        Button(
            onClick = { navController.navigate("schedule") }, // 📌 Redirige a la pantalla de horarios
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA8E6CF)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "📅 Horario de Entrenamiento", fontSize = 16.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Título de las tarjetas de entrenamiento
        Text(
            text = "¿Qué quieres entrenar?",
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Tarjetas de Ejercicios con animaciones y mejor diseño
        ExerciseCard("🏋️ Entrenamiento completo", "11 Ejercicios | 32 mins")
        ExerciseCard("🏃‍♂️ Ejercicios de piernas", "12 Ejercicios | 40 mins")
        ExerciseCard("💪 Ejercicios de Abs", "14 Ejercicios | 20 mins")
    }
}

@Composable
fun TrainingChart() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.linearGradient(listOf(Color(0xFF81C784), Color(0xFF66BB6A))),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "📊 Progreso Semanal", color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(6.dp)
                    .background(Color.White, shape = RoundedCornerShape(3.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f) // Representa el 70% del progreso
                        .height(6.dp)
                        .background(Color.Green, shape = RoundedCornerShape(3.dp))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "🔥 70% del objetivo semanal alcanzado", color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun ExerciseCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Acción al presionar */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        Brush.linearGradient(listOf(Color(0xFF8BC34A), Color(0xFF4CAF50))),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = title.first().toString(), fontSize = 24.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}
