package com.example.vitalife

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vitalife.R

@Composable
fun MensajeFinalScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen motivacional
        Image(
            painter = painterResource(id = R.drawable.motivacion), // Asegúrate de tener esta imagen en res/drawable
            contentDescription = "Imagen motivacional",
            modifier = Modifier.size(240.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de felicitación
        Text(
            text = "Enhorabuena, has terminado tu entrenamiento",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Frase inspiradora
        Text(
            text = "El ejercicio es el rey y la nutrición es la reina. Combina los dos y tendrás un reino",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Text(
            text = "- Jack Lalanne",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para el siguiente entrenamiento
        Button(
            onClick = { navController.navigate("entrenamiento") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Siguiente entrenamiento", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botón para salir
        Button(
            onClick = { /* Acción para cerrar la app */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), // Rojo
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Salir", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botón para regresar al inicio
        Button(
            onClick = { navController.navigate("inicio") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // Azul
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Regresar al Inicio", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview
@Composable
fun VistaPreviaMensajeFinal() {
    val navController = rememberNavController()
    MensajeFinalScreen(navController)
}