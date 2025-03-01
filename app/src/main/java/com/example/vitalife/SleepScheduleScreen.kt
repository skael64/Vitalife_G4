package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SleepApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "tracker") {
        composable("tracker") {
            SleepTrackerScreen(
                onCheckClick = { navController.navigate("schedule") }
            )
        }
        composable("schedule") {
            SleepScheduleScreen(
                onBackClick = { navController.navigateUp() },
                onAddClick = { navController.navigate("add-alarm") }
            )
        }
        composable("add-alarm") {
            AddAlarmScreen(
                onBackClick = { navController.navigateUp() },
                onAddClick = {
                    // Aquí podrías agregar lógica para guardar la alarma si tuvieras backend
                    navController.navigateUp()
                }
            )
        }
    }
}

@Composable
fun SleepScheduleScreen(onBackClick: () -> Unit, onAddClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Horario de sueño",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Ideal Sleep Time Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Horas ideales para dormir",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "⭐",
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Text(
                        text = "8horas 30minutos",
                        color = Color(0xFF81C784),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "🌙",
                    fontSize = 40.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Weekly Calendar
        Text(
            text = "Tu horario",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Mar", "Mie", "Jue", "Vie", "Sab", "Dom").forEachIndexed { index, day ->
                DayItem(
                    day = day,
                    date = "${11 + index}",
                    isSelected = index == 3
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sleep Schedule Items
        ScheduleItem(
            icon = "🛏️",
            title = "Hora de dormir",
            time = "09:00pm",
            remainingTime = "en 6horas 22minutos"
        )

        Spacer(modifier = Modifier.height(12.dp))

        ScheduleItem(
            icon = "⏰",
            title = "Alarma",
            time = "05:10am",
            remainingTime = "en 14horas 30minutos"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sleep Duration Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Tendrás 8 horas y 10 minutos\npara esta noche.",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.96f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFF81C784),
                    trackColor = Color(0xFFE0E0E0)
                )
            }
        }

        // Floating Action Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF81C784),
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun DayItem(
    day: String,
    date: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFF81C784) else Color(0xFFE8F5E9))
            .padding(8.dp)
    ) {
        Text(
            text = day,
            fontSize = 14.sp,
            color = if (isSelected) Color.White else Color.Black
        )
        Text(
            text = date,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaSleepScheduleScreen() {
    SleepScheduleScreen(
        onBackClick = { /* Lógica de retroceso en vista previa (puedes dejarlo vacío) */ },
        onAddClick = { /* Lógica de añadir alarma en vista previa (puedes dejarlo vacío) */ }
    )
}

