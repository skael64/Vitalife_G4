package com.example.vitalife

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DescripcionScreen(navController: NavController, ejercicio: String) {

    val descripcion = "El $ejercicio es un ejercicio efectivo para mejorar la resistencia y la fuerza. Sigue estos pasos para hacerlo correctamente."

    val pasos = when (ejercicio) {
        "Salto de tijera" -> listOf(
            "Extiende los brazos al inicio del movimiento.",
            "Salta separando las piernas y levantando los brazos.",
            "Vuelve a la posición inicial controlando el impacto.",
            "Repite a un ritmo constante por el tiempo indicado."
        )
        "Squats" -> listOf(
            "Coloca los pies a la altura de los hombros.",
            "Baja manteniendo la espalda recta y empujando las caderas hacia atrás.",
            "Mantén las rodillas alineadas con los pies.",
            "Sube lentamente a la posición inicial."
        )
        "Calentamiento" -> listOf(
            "Realiza movimientos articulares para activar el cuerpo.",
            "Haz estiramientos dinámicos.",
            "Ejecuta saltos suaves o jogging en el sitio.",
            "Prepárate mentalmente para el entrenamiento."
        )
        else -> listOf("Sigue los pasos correctos para realizar el ejercicio de manera efectiva.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // Título y calorías
        Text(text = ejercicio, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Fácil | 390 Calorías Quemadas", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción
        Text(text = "Descripción", fontSize = 18.sp, color = Color.Black)
        Text(text = descripcion, fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de pasos
        Text(text = "Cómo hacerlo", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(pasos.indices.toList()) { index ->
                PasoItem(index + 1, pasos[index])
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar el ejercicio
        Button(
            onClick = { navController.navigate("cronometro") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Iniciar Ejercicio", fontSize = 18.sp, color = Color.White)
        }
    }
}

// Composable para cada paso del ejercicio
@Composable
fun PasoItem(numero: Int, texto: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = "%02d".format(numero), fontSize = 16.sp, color = Color(0xFF4CAF50))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = texto, fontSize = 16.sp)
    }
}

@Preview
@Composable
fun VistaPreviaDescripcionScreen() {
    val navController = rememberNavController()
    DescripcionScreen(navController, "Salto de tijera")
}

