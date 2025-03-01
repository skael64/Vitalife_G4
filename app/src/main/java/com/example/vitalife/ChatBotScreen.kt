package com.example.vitalife

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

// Data class para los mensajes
data class Message(val text: String, val isUser: Boolean)

@Composable
fun ChatBotScreen(navController: NavController) {
    val messages = remember { mutableStateListOf<Message>() }
    val userInput = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Cabecera
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

        // Historial de mensajes
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

        // Campo de entrada de texto
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userInput.value,
                onValueChange = { userInput.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Escribe tu pregunta...") }
            )

            Button(
                onClick = {
                    if (userInput.value.isNotBlank()) {
                        val question = userInput.value
                        messages.add(Message(question, true))
                        userInput.value = ""

                        coroutineScope.launch {
                            val answer = generateAnswer(question)
                            messages.add(Message(answer, false))
                        }
                    }
                }
            ) {
                Text("Enviar")
            }
        }

        // Sección de preguntas rápidas
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Section(
                title = "Preguntas rápidas",
                options = listOf(
                    "Ejercicios para brazos",
                    "Masajes para dolor de cuello",
                    "Rutina de calentamiento",
                    "Ejercicios abdominales",
                    "Estiramientos post-entreno"
                ),
                onOptionClick = { question ->
                    messages.add(Message(question, true))
                    coroutineScope.launch {
                        val answer = generateAnswer(question)
                        messages.add(Message(answer, false))
                    }
                }
            )
        }
    }
}

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

@Composable
fun Section(
    title: String,
    options: List<String>,
    onOptionClick: (String) -> Unit
) {
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

private fun generateAnswer(question: String): String {
    return when {
        question.contains("brazos", ignoreCase = true) -> """
            💪 Ejercicios para brazos:
            • Flexiones de diamante (3x12)
            • Curl de bíceps con mancuernas (4x10)
            • Fondos en silla (3x15)
            • Extensiones de tríceps (4x12)
            Descansa 60 seg entre series
        """.trimIndent()

        question.contains("cuello", ignoreCase = true) -> """
            😌 Masajes para dolor de cuello:
            1. Auto-masaje con dedos: Presiona suavemente en círculos desde la base del cráneo hacia los hombros
            2. Estiramiento lateral: Inclina la cabeza hacia un lado manteniendo 30 segundos
            3. Uso de pelota de tenis: Recostado boca arriba, coloca la pelota en zonas tensas
            ⚠️ Si persiste, consulta un especialista
        """.trimIndent()

        question.contains("calentamiento", ignoreCase = true) -> """
            🔥 Rutina de calentamiento (5-10 min):
            • Rotaciones de cuello y hombros
            • Círculos con brazos
            • Sentadillas sin peso (2x15)
            • Jumping jacks (1 minuto)
            • Estocadas dinámicas (10 por pierna)
        """.trimIndent()

        question.contains("abdominales", ignoreCase = true) -> """
            🏋️ Ejercicios abdominales:
            • Plancha frontal (3x30 seg)
            • Crunch bicicleta (3x20)
            • Elevación de piernas (3x15)
            • Russian twists (4x20)
            Mantén contracción constante
        """.trimIndent()

        question.contains("estiramientos", ignoreCase = true) -> """
            🧘 Estiramientos post-entreno:
            • Gato-vaca (espalda): 2x10 rep
            • Estiramiento de isquiotibiales: 30 seg por pierna
            • Estiramiento de pecho: 30 seg cada lado
            • Torsión espinal: 20 seg cada lado
        """.trimIndent()

        else -> """
            🤖 ¿Necesitas ayuda con?:
            - Rutinas de ejercicio
            - Técnicas de recuperación
            - Planes de entrenamiento
            - Consejos nutricionales básicos
            Escribe tu pregunta específica
        """.trimIndent()
    }
}

@Preview
@Composable
fun VistaPreviaChatBotScreen() {
    val navController = rememberNavController()
    ChatBotScreen(navController = navController)
}
