package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.navigation.compose.rememberNavController

@Composable
fun AddScheduleScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(15, 30)) }
    var selectedWorkout by remember { mutableStateOf("Entrenamiento Piernas") }
    var selectedDifficulty by remember { mutableStateOf("Principiante") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Encabezado con botón de cierre
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Agregar horario",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Selección de Fecha
        DateSelector(selectedDate)

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Selección de Hora
        TimeSelector(selectedTime)

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Detalles del Entrenamiento
        TrainingDetails(
            selectedWorkout,
            selectedDifficulty,
            onWorkoutChange = { selectedWorkout = it },
            onDifficultyChange = { selectedDifficulty = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        // 🔹 Botón Guardar
        GradientButton(text = "Guardar", onClick = { navController.popBackStack() })
    }
}

@Composable
fun DateSelector(selectedDate: LocalDate) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("📅", fontSize = 18.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = selectedDate.format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy")),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}

@Composable
fun TimeSelector(selectedTime: LocalTime) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50)
        )
    }
}

@Composable
fun TrainingDetails(
    selectedWorkout: String,
    selectedDifficulty: String,
    onWorkoutChange: (String) -> Unit,
    onDifficultyChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TrainingOption("🏋️", "Elegir entrenamiento", selectedWorkout) { onWorkoutChange("Nuevo Entrenamiento") }
        TrainingOption("📈", "Dificultad", selectedDifficulty) { onDifficultyChange("Intermedio") }

        TrainingOption("🔄", "Repeticiones personalizadas", "Configurar") {}
        TrainingOption("🏋️‍♂️", "Personalizar pesas", "Configurar") {}
    }
}

@Composable
fun TrainingOption(icon: String, title: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = value, color = Color.Gray, fontSize = 14.sp)
        }
        Text(">", fontSize = 18.sp, color = Color.Gray)
    }
}

@Composable
fun GradientButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(listOf(Color(0xFF81C784), Color(0xFF4CAF50))),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun VistaPreviaAddScheduleScreen() {
    val navController = rememberNavController()
    AddScheduleScreen(navController = navController)
}