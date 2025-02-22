package com.example.vitalife.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Añade esto fuera del composable
data class Message(val text: String, val isUser: Boolean)

@Composable
fun ChatBotScreen(navController: NavController) {
    val messages = remember { mutableStateListOf<Message>() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Nueva cabecera con botón de regreso
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Fitness Bot",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "● Online",
                color = Color.Green,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Historial del chat (mensajes)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                ChatMessage(message = message)
            }
        }

        // Sección de preguntas rápidas
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Section(
                title = "Preguntas frecuentes",
                options = listOf(
                    "¿Cuál es el mejor momento para entrenar?",
                    "¿Qué ejercicios puedo hacer hoy?",
                    "¿Cuánto tiempo debo descansar entre series?",
                    "¿Es necesario hacer cardio todos los días?"
                ),
                onOptionClick = { question ->
                    messages.add(Message(question, true))
                    coroutineScope.launch {
                        val answer = when (question) {
                            "¿Cuál es el mejor momento para entrenar?" -> "El mejor momento es cuando tengas más energía. Muchos prefieren mañanas para activar el metabolismo."
                            "¿Qué ejercicios puedo hacer hoy?" -> "Sugiero:\n- Sentadillas 3x12\n- Flexiones 3x10\n- Plancha 3x30 seg\n¡Adapta según tu nivel!"
                            "¿Cuánto tiempo debo descansar entre series?" -> "Fuerza: 2-3 min\nHipertrofia: 60-90 seg\nResistencia: 30-60 seg"
                            "¿Es necesario hacer cardio todos los días?" -> "No. Ideal 3-5 veces/semana. Combina con fuerza."
                            else -> "Reformula tu pregunta, por favor"
                        }
                        messages.add(Message(answer, false))
                    }
                }
            )

            // Botón de acción rápida
            Button(
                onClick = {
                    messages.add(Message("¿Cómo empezar un programa?", true))
                    messages.add(Message(
                        """1. Define tus metas
                        2. Elige 3-5 ejercicios básicos
                        3. Empieza con 3 días/semana
                        4. Progresa gradualmente
                        ¡Vamos!""", false
                    ))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Crear plan inicial")
            }
        }
    }
}

// Composable para mensajes individuales
@Composable
fun ChatMessage(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (message.isUser) Color(0xFF4CAF50) else Color.LightGray,
            modifier = Modifier.sizeIn(maxWidth = 280.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) Color.White else Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// Composable modificado para las secciones
@Composable
fun Section(title: String, options: List<String>, onOptionClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        options.forEach { option ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onOptionClick(option) },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = option,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}