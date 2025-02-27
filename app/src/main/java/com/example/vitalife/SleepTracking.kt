package com.example.vitalife

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun SleepTrackerScreen(onCheckClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(24.dp))
        SleepChart()
        Spacer(modifier = Modifier.height(24.dp))
        GradientSleepInfoCard()
        Spacer(modifier = Modifier.height(16.dp))
        DailySleepScheduleCard(onCheckClick = onCheckClick)
        Spacer(modifier = Modifier.height(24.dp))
        TodaySchedule()
    }
}

@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = {}) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "Seguimiento de Sueño",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SleepChart() {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                val days = listOf("Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab")
                val entries = listOf(
                    Entry(0f, 4f),
                    Entry(1f, 6f),
                    Entry(2f, 5f),
                    Entry(3f, 7f),
                    Entry(4f, 6f),
                    Entry(5f, 8f),
                    Entry(6f, 7f)
                )

                val dataSet = LineDataSet(entries, "").apply {
                    color = android.graphics.Color.parseColor("#81C784")
                    setCircleColor(android.graphics.Color.parseColor("#81C784"))
                    lineWidth = 2f
                    circleRadius = 4f
                    setDrawValues(false)
                    setDrawFilled(true)
                    fillAlpha = 50
                    fillColor = android.graphics.Color.parseColor("#81C784")
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }

                data = LineData(dataSet)
                description.isEnabled = false
                legend.isEnabled = false

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = IndexAxisValueFormatter(days)
                    textColor = android.graphics.Color.GRAY
                    textSize = 12f
                    setDrawGridLines(false)
                }

                axisLeft.apply {
                    axisMinimum = 0f
                    axisMaximum = 12f
                    textColor = android.graphics.Color.GRAY
                    setDrawGridLines(true)
                    gridColor = android.graphics.Color.LTGRAY
                    gridLineWidth = 0.5f
                }

                axisRight.isEnabled = false
                setTouchEnabled(true)
                setPinchZoom(false)
                setScaleEnabled(false)

                invalidate()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun GradientSleepInfoCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF81D4FA),
                        Color(0xFF81C784)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Sueño de la última noche",
                color = Color.White,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "8h 20m",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DailySleepScheduleCard(onCheckClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Horario de sueño diario",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = onCheckClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81D4FA)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "Check", color = Color.White)
            }
        }
    }
}

@Composable
fun TodaySchedule() {
    Column {
        Text(
            text = "Horario de hoy",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ScheduleItem(
            icon = "🛏️",
            title = "Hora de dormir",
            time = "09:00pm",
            remainingTime = "en 6 horas 22 minutos"
        )
        Spacer(modifier = Modifier.height(12.dp))
        ScheduleItem(
            icon = "⏰",
            title = "Alarma",
            time = "05:10am",
            remainingTime = "en 14 horas 30 minutos"
        )
    }
}

@Composable
fun ScheduleItem(
    icon: String,
    title: String,
    time: String,
    remainingTime: String
) {
    var isEnabled by remember { mutableStateOf(true) }
    var showMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FFF8))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$title, $time",
                    fontWeight = FontWeight.Medium,
                    color = if (isEnabled) Color.Black else Color.Gray
                )
                Text(
                    text = remainingTime,
                    fontSize = 14.sp,
                    color = if (isEnabled) Color.Gray else Color.LightGray
                )
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = if (isEnabled) Color.DarkGray else Color.Gray
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text("Editar")
                            }
                        },
                        onClick = {
                            showDialog = true
                            dialogType = "edit"
                            showMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text("Eliminar")
                            }
                        },
                        onClick = {
                            showDialog = true
                            dialogType = "delete"
                            showMenu = false
                        }
                    )
                }
            }

            Switch(
                checked = isEnabled,
                onCheckedChange = { isEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF81C784),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }

    // Dialog for Edit
    if (showDialog && dialogType == "edit") {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Editar $title") },
            text = { Text("Aquí iría un formulario para editar la hora de $title. Esta es una simulación frontend.") },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784))
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialog for Delete
    if (showDialog && dialogType == "delete") {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Eliminar $title") },
            text = { Text("¿Estás seguro de que deseas eliminar esta programación? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}