package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import java.time.format.DateTimeFormatter
import androidx.navigation.compose.rememberNavController

@Composable
fun WorkoutScheduleScreen(navController: NavController) {
    var selectedDay by remember { mutableStateOf(LocalDate.now()) }
    var showEventDialog by remember { mutableStateOf(false) }

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
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Horario de Entrenamiento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Calendario semanal con cambio de mes
        WorkoutCalendarHeader(selectedDay, onDateChange = { selectedDay = it })

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Lista de horarios con eventos
        WorkoutScheduleList(onEventClick = { showEventDialog = true })

        // 🔹 Botón flotante para agregar entrenamientos
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { navController.navigate("addSchedule") },
                containerColor = Color(0xFF4CAF50),
                shape = CircleShape,
                modifier = Modifier.shadow(8.dp, CircleShape)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        }

        // 🔹 Popup modal al hacer clic en un evento
        if (showEventDialog) {
            WorkoutEventDetailsDialog(onDismiss = { showEventDialog = false })
        }
    }
}

@Composable
fun WorkoutCalendarHeader(selectedDay: LocalDate, onDateChange: (LocalDate) -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val daysOfWeek = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onDateChange(selectedDay.minusMonths(1)) }) {
                Text(text = "<", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Text(text = selectedDay.format(formatter), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { onDateChange(selectedDay.plusMonths(1)) }) {
                Text(text = ">", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            daysOfWeek.forEach { day ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            Brush.horizontalGradient(
                                if (day == "Vie") listOf(Color(0xFF81C784), Color(0xFF4CAF50))
                                else listOf(Color.LightGray, Color.Gray)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = day, color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun WorkoutScheduleList(onEventClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        for (hour in 6..20) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = String.format("%02d:00 %s", hour, if (hour < 12) "AM" else "PM"))
                Spacer(modifier = Modifier.weight(1f))
                if (hour == 15) { // Evento a las 3 PM
                    Box(
                        modifier = Modifier
                            .clickable { onEventClick() }
                            .background(
                                brush = Brush.horizontalGradient(listOf(Color(0xFF81C784), Color(0xFF4CAF50))),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(text = "Entrenamiento piernas, 3pm", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutEventDetailsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("Marcar como completado", color = Color.White)
            }
        },
        text = {
            Column {
                Text("Horario de Entrenamiento", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Entrenamiento Piernas", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🕒 Hoy | 03:00PM", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    )
}

@Preview
@Composable
fun VistaPreviaWorkoutScheduleScreen() {
    val navController = rememberNavController()
    WorkoutScheduleScreen(navController = navController)
}