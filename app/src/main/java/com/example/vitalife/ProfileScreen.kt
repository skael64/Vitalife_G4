package com.example.vitalife

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController) {

    var fecha by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Completa tu Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer)

        Spacer(modifier = Modifier.height(16.dp))

        //  FECHA DE NACIMIENTO
        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // PESO
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // TALLA
        OutlinedTextField(
            value = talla,
            onValueChange = { talla = it },
            label = { Text("Talla") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Boton estilizado
        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50), // Verde claro
                contentColor = Color.White // Color del texto
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Siguiente")
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}