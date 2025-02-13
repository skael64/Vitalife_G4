package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun AddScheduleScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado con botón de cierre
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "✖",
                fontSize = 24.sp,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Text(
                text = "Agregar horario",
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Sección de selección de hora
        Text(text = "Hora", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        var selectedHour by remember { mutableStateOf("3:30 PM") }
        Text(text = selectedHour, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de detalles del entrenamiento
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Color(0xFFDFFFE2), shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text("Detalles del Entrenamiento", fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Text("🏋️‍♂️ Elegir entrenamiento: Entrenamiento piernas", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("📊 Dificultad: Principiante", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("🔢 Repeticiones personalizadas", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("🏋️‍♀️ Personalizar pesas", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de guardar con degradado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Brush.linearGradient(listOf(Color.Blue, Color.Green)), shape = RoundedCornerShape(10.dp))
        ) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Guardar", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}
