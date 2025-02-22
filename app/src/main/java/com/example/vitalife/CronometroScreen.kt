package com.example.vitalife

import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CronometroScreen(navController: NavController, ejercicio: String) {
    var tiempoRestante by remember { mutableStateOf(5 * 60 * 1000L) } // 5 minutos en milisegundos
    var enEjecucion by remember { mutableStateOf(false) }
    var temporizador: CountDownTimer? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título del ejercicio
        Text(text = ejercicio, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(text = "Fácil | 390 Calorías Quemadas", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(20.dp))

        // Cronómetro
        Text(
            text = formatTiempo(tiempoRestante),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50) // Verde
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón Iniciar / Pausar
        Button(
            onClick = {
                if (!enEjecucion) {
                    enEjecucion = true
                    temporizador = object : CountDownTimer(tiempoRestante, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            tiempoRestante = millisUntilFinished
                        }

                        override fun onFinish() {
                            tiempoRestante = 0L
                            enEjecucion = false
                        }
                    }.start()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = if (enEjecucion) "En Progreso..." else "Iniciar", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botón Terminar
        Button(
            onClick = {
                temporizador?.cancel()
                navController.navigate("mensajeFinal")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), // Rojo
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Terminar", fontSize = 18.sp, color = Color.White)
        }
    }
}

// Función para formatear el tiempo en mm:ss
fun formatTiempo(millis: Long): String {
    val minutos = (millis / 1000) / 60
    val segundos = (millis / 1000) % 60
    return String.format("%02d:%02d", minutos, segundos)
}

@Preview
@Composable
fun VistaPreviaCronometro() {
    val navController = rememberNavController()
    CronometroScreen(navController, "Salto de tijera")
}