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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Gráfico de entrenamiento
        TrainingChart()
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Botón de horario de entrenamiento
        Button(
            onClick = { navController.navigate("workoutSchedule") },
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Tarjetas de Ejercicios con animaciones y mejor diseño
        WorkoutExerciseCard("🏋️ Entrenamiento completo", "11 Ejercicios | 32 mins")
        WorkoutExerciseCard("🏃‍♂️ Ejercicios de piernas", "12 Ejercicios | 40 mins")
        WorkoutExerciseCard("💪 Ejercicios de Abs", "14 Ejercicios | 20 mins")
    }
}

@Composable
fun TrainingChart(progress: Float = 0.7f) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Progreso semanal de entrenamiento",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Barra de progreso con degradado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFE0E0E0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress) // Representa el porcentaje de progreso
                        .height(8.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF81C784), Color(0xFF4CAF50))
                            ), shape = RoundedCornerShape(4.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "🔥 ${(progress * 100).toInt()}% del objetivo semanal alcanzado",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF388E3C)
            )
        }
    }
}

@Composable
fun WorkoutExerciseCard(title: String, subtitle: String) {
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
                Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

// 🔹 Navegación en WorkoutTrackerApp
@Composable
fun WorkoutTrackerApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "workoutTracker") {
        composable("workoutTracker") { WorkoutTrackerScreen(navController) }
        composable("workoutSchedule") { WorkoutScheduleScreen(navController) }
        composable("addSchedule") { AddScheduleScreen(navController) }
    }
}

@Preview
@Composable
fun VistaPreviaWorkoutTrackerScreen() {
    val navController = rememberNavController()
    WorkoutTrackerScreen(navController = navController)
}