package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(22, 0)) }
    var selectedDays by remember { mutableStateOf(setOf("Lun", "Mar", "Mie", "Jue", "Vie")) }
    var vibrationEnabled by remember { mutableStateOf(true) }

    // Get current time for sleep duration calculation
    val currentTime = remember { LocalTime.now() }

    // Calculate sleep duration
    val sleepDuration by remember(currentTime, selectedTime) {
        derivedStateOf {
            calculateSleepDuration(currentTime, selectedTime)
        }
    }

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
            IconButton(onClick = onBackClick) {
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
                        text = "Sonar alarma a las:",
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

        // Horas de sueño (calculated automatically)
        Card(
            modifier = Modifier.fillMaxWidth(),
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
                        text = sleepDuration,
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

        // Modern Clock-style Time Picker Dialog
        if (showTimePicker) {
            ModernTimePickerDialog(
                initialTime = selectedTime,
                onDismiss = { showTimePicker = false },
                onTimeSelected = {
                    selectedTime = it
                    showTimePicker = false
                }
            )
        }

        // Botón Agregar
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onAddClick,
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
fun ModernTimePickerDialog(
    initialTime: LocalTime,
    onDismiss: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    var hour by remember { mutableStateOf(initialTime.hour) }
    var minute by remember { mutableStateOf(initialTime.minute) }
    var isPM by remember { mutableStateOf(initialTime.hour >= 12) }

    // Convert to 12-hour format for display
    var displayHour by remember(hour) {
        mutableStateOf(if (hour % 12 == 0) 12 else hour % 12)
    }

    // Keep track of which wheel is active (hour or minute)
    var isHourSelection by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = "Seleccionar hora",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Display current selection
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = String.format("%02d", displayHour),
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isHourSelection) Color(0xFF81C784) else Color.Gray,
                        modifier = Modifier
                            .clickable { isHourSelection = true }
                            .padding(8.dp)
                    )
                    Text(
                        text = ":",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Text(
                        text = String.format("%02d", minute),
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!isHourSelection) Color(0xFF81C784) else Color.Gray,
                        modifier = Modifier
                            .clickable { isHourSelection = false }
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "AM",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (!isPM) Color(0xFF81C784) else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    isPM = false
                                    hour = if (hour >= 12) hour - 12 else hour
                                }
                                .padding(4.dp)
                        )
                        Text(
                            text = "PM",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isPM) Color(0xFF81C784) else Color.Gray,
                            modifier = Modifier
                                .clickable {
                                    isPM = true
                                    hour = if (hour < 12) hour + 12 else hour
                                }
                                .padding(4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Number selection wheel
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(280.dp)
                        .padding(16.dp)
                ) {
                    // Background circle
                    Box(
                        modifier = Modifier
                            .size(240.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8F5E9))
                    )

                    // Center circle
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF81C784))
                    )

                    // Numbers
                    if (isHourSelection) {
                        // Hours (1-12)
                        for (i in 1..12) {
                            val angleRad = Math.PI * 2 * (i - 3) / 12
                            val radius = 100f
                            val x = cos(angleRad.toFloat()) * radius
                            val y = sin(angleRad.toFloat()) * radius

                            val isSelected = displayHour == i

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .offset(x = x.dp, y = y.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color(0xFF81C784) else Color.Transparent)
                                    .clickable {
                                        displayHour = i
                                        // Convert to 24 hour format
                                        hour = if (isPM) {
                                            if (i == 12) 12 else i + 12
                                        } else {
                                            if (i == 12) 0 else i
                                        }
                                    }
                            ) {
                                Text(
                                    text = "$i",
                                    color = if (isSelected) Color.White else Color.Black,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    } else {
                        // Minutes (in increments of 5)
                        for (i in 0..11) {
                            val minuteValue = i * 5
                            val angleRad = Math.PI * 2 * (i - 3) / 12
                            val radius = 100f
                            val x = cos(angleRad.toFloat()) * radius
                            val y = sin(angleRad.toFloat()) * radius

                            val isSelected = minute / 5 * 5 == minuteValue

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .offset(x = x.dp, y = y.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color(0xFF81C784) else Color.Transparent)
                                    .clickable {
                                        minute = minuteValue
                                    }
                            ) {
                                Text(
                                    text = String.format("%02d", minuteValue),
                                    color = if (isSelected) Color.White else Color.Black,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val finalHour = if (isPM) {
                    if (displayHour == 12) 12 else displayHour + 12
                } else {
                    if (displayHour == 12) 0 else displayHour
                }
                onTimeSelected(LocalTime.of(finalHour, minute))
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

// Helper function to calculate sleep duration based on current time and selected alarm time
private fun calculateSleepDuration(currentTime: LocalTime, alarmTime: LocalTime): String {
    var hours = if (alarmTime.isBefore(currentTime)) {
        // If alarm is set for tomorrow
        24 - currentTime.hour + alarmTime.hour
    } else {
        alarmTime.hour - currentTime.hour
    }

    var minutes = alarmTime.minute - currentTime.minute

    // Adjust for minute differences
    if (minutes < 0) {
        minutes += 60
        hours -= 1
    }

    return "${hours}horas ${minutes}minutos"
}