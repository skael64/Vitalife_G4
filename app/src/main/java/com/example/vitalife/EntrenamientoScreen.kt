package com.example.vitalife

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun EntrenamientoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Entrenamiento Completo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Carrusel de Equipos Necesarios
        Text(text = "Necesitarás", fontSize = 18.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        val equipos = listOf(
            R.drawable.pesas to "Pesas",
            R.drawable.cuerda to "Cuerda de salto",
            R.drawable.botella to "Botella de agua"
        )

        LazyRow {
            items(equipos) { equipo ->
                EquipoCard(equipo.first, equipo.second)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Ejercicios
        Text(text = "Ejercicios", fontSize = 18.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        val ejercicios = listOf(
            "Calentamiento" to "05:00",
            "Salto de tijera" to "12x",
            "Squats" to "20x"
        )

        LazyColumn {
            items(ejercicios) { ejercicio ->
                EjercicioItem(ejercicio.first, ejercicio.second)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ir a la descripción
        Button(
            onClick = { navController.navigate("descripcion") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Iniciar Entrenamiento", fontSize = 18.sp, color = Color.White)
        }
    }
}

// Composable para los equipos en el carrusel
@Composable
fun EquipoCard(imagenRes: Int, nombre: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp)
    ) {
        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = nombre,
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = nombre, fontSize = 14.sp)
    }
}

// Composable para la lista de ejercicios
@Composable
fun EjercicioItem(nombre: String, repeticiones: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = nombre, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Text(text = repeticiones, fontSize = 16.sp, color = Color.Gray)
    }
}

@Preview
@Composable
fun VistaPrevia() {
    val navController = rememberNavController()
    EntrenamientoScreen(navController = navController)
}
