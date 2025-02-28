package com.example.vitalife

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NotificationsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Notificaciones", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        NotificationItem("Ups, te has perdido tu horario de entrenamiento", "3 Abril")
        NotificationItem("No te pierdas tu horario de entrenamiento", "Hace 3 horas")
        NotificationItem("¡Felicitaciones! Terminaste tu entrenamiento", "29 Mayo")
    }
}

@Composable
fun NotificationItem(title: String, date: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold)
        Text(date, fontSize = 12.sp, color = androidx.compose.ui.graphics.Color.Gray)
        Divider()
    }
}

@Preview
@Composable
fun VistaPreviaNotificationsScreen() {
    NotificationsScreen() // No es necesario pasar el navController
}


