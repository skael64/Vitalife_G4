package com.example.vitalife.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ChatBotScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Chat", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text("● Online", color = Color.Green, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Section(title = "Explora", options = listOf("Registra tu Sueño", "Programa tus Entrenamientos"))
        Section(title = "Personaliza", options = listOf("Configura tu horario ideal de sueño", "Elige ejercicios según tus metas", "Ajusta tus metas"))
        Section(title = "Pregúntame", options = listOf("¿Cuál es el mejor momento para entrenar?", "¿Qué ejercicios puedo hacer hoy?"))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Acción de chat */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("¿Listo para alcanzar tus metas? ¡Hablemos!", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text("Volver")
        }
    }
}

@Composable
fun Section(title: String, options: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        options.forEach { option ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .clickable { /* Acción */ }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(option, fontSize = 16.sp, color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
