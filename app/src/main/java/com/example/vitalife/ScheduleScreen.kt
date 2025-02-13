package com.example.vitalife

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O) // ✅ Asegura que se usa API 26+
@Composable
fun ScheduleScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Encabezado con botón de regreso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "< Volver",
                fontSize = 18.sp,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Horario de Entrenamiento",
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Calendario con cambio de mes
        WeekCalendar()

        // 🔹 Lista de horarios disponibles
        ScheduleList(navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekCalendar() {
    val days = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
    var selectedDay by remember { mutableStateOf(4) } // Viernes por defecto
    var currentMonth by remember { mutableStateOf(LocalDate.now()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
            Text(text = "<", fontSize = 20.sp)
        }
        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            fontSize = 18.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
            Text(text = ">", fontSize = 20.sp)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        days.forEachIndexed { index, day ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        brush = if (index == selectedDay)
                            Brush.horizontalGradient(listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))) // ✅ Degradado correcto
                        else Brush.horizontalGradient(listOf(Color.LightGray, Color.Gray)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable { selectedDay = index }
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day, color = Color.White, fontSize = 16.sp)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ScheduleList(navController: NavController) {
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
                            .background(
                                brush = Brush.horizontalGradient(listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))),
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

    // 🔹 Botón flotante para agregar entrenamientos
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { navController.navigate("addSchedule") },
            containerColor = Color(0xFF4CAF50),
            shape = CircleShape
        ) {
            Text("+", fontSize = 24.sp, color = Color.White)
        }
    }
}
