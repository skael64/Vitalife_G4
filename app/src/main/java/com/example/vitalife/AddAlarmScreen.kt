package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(navController: NavController) {
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(22, 0)) }
    var showDurationPicker by remember { mutableStateOf(false) }
    var selectedDuration by remember { mutableStateOf("8horas 30minutos") }
    var selectedDays by remember { mutableStateOf(setOf("Lun", "Mar", "Mie", "Jue", "Vie")) }
    var vibrationEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Agregar Alarma",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hora de Dormir
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showTimePicker = true },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFF81C784)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hora de Dormir",
                        color = Color.Gray
                    )
                    Text(
                        text = selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Horas de sueño
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDurationPicker = true },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFF81C784)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Horas de sueño",
                        color = Color.Gray
                    )
                    Text(
                        text = selectedDuration,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Repetir
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Repetir",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom").forEach { day ->
                        DayChip(
                            day = day,
                            selected = selectedDays.contains(day),
                            onSelectedChange = { selected ->
                                selectedDays = if (selected) {
                                    selectedDays + day
                                } else {
                                    selectedDays - day
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Vibrar al sonar la alarma
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vibrar al sonar la alarma",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = vibrationEnabled,
                    onCheckedChange = { vibrationEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF81C784)
                    )
                )
            }
        }

        // Time Picker Dialog
        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                onTimeSelected = {
                    selectedTime = it
                    showTimePicker = false
                }
            )
        }

        // Duration Picker Dialog
        if (showDurationPicker) {
            DurationPickerDialog(
                onDismiss = { showDurationPicker = false },
                onDurationSelected = {
                    selectedDuration = it
                    showDurationPicker = false
                }
            )
        }

        // Botón Agregar
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {navController.popBackStack()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Agregar",
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun DayChip(
    day: String,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) Color(0xFF81C784) else Color(0xFFE0E0E0))
            .clickable { onSelectedChange(!selected) }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            color = if (selected) Color.White else Color.Black,
            fontSize = 14.sp
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    var selectedHour by remember { mutableStateOf(22) }
    var selectedMinute by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar hora") },
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Selector de hora
                NumberPicker(
                    value = selectedHour,
                    onValueChange = { selectedHour = it },
                    range = 0..23
                )
                Text(":")
                // Selector de minutos
                NumberPicker(
                    value = selectedMinute,
                    onValueChange = { selectedMinute = it },
                    range = 0..59
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(LocalTime.of(selectedHour, selectedMinute))
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DurationPickerDialog(
    onDismiss: () -> Unit,
    onDurationSelected: (String) -> Unit
) {
    var selectedHours by remember { mutableStateOf(8) }
    var selectedMinutes by remember { mutableStateOf(30) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar duración") },
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Selector de horas
                NumberPicker(
                    value = selectedHours,
                    onValueChange = { selectedHours = it },
                    range = 0..12
                )
                Text("horas")
                // Selector de minutos
                NumberPicker(
                    value = selectedMinutes,
                    onValueChange = { selectedMinutes = it },
                    range = 0..59
                )
                Text("min")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDurationSelected("${selectedHours}horas ${selectedMinutes}minutos")
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange
) {
    Column {
        range.forEach { number ->
            Text(
                text = String.format("%02d", number),
                modifier = Modifier
                    .clickable { onValueChange(number) }
                    .padding(vertical = 4.dp),
                color = if (number == value) Color(0xFF81C784) else Color.Gray,
                fontWeight = if (number == value) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Preview
@Composable
fun VistaPreviaAddAlarmScreen() {
    val navController = rememberNavController()
    AddAlarmScreen(navController = navController)
}

