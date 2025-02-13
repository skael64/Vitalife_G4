package com.example.vitalife

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun SleepTrackerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "<",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Seguimiento de Sueño",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SleepChart()
        Spacer(modifier = Modifier.height(16.dp))
        SleepInfoCard("Sueño de la última noche", "8h 20m")
        Spacer(modifier = Modifier.height(8.dp))
        SleepScheduleButton()
        Spacer(modifier = Modifier.height(16.dp))
        SleepScheduleItem("Hora de dormir", "09:00pm", "en 6 horas 22 minutos")
        Spacer(modifier = Modifier.height(8.dp))
        SleepScheduleItem("Alarma", "05:10am", "en 14 horas 30 minutos")
    }
}

@Composable
fun SleepChart() {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                val entries = listOf(
                    Entry(1f, 6f),
                    Entry(2f, 7.5f),
                    Entry(3f, 8f),
                    Entry(4f, 5.5f),
                    Entry(5f, 7f),
                    Entry(6f, 8f)
                )
                val dataSet = LineDataSet(entries, "Horas de Sueño").apply {
                    color = android.graphics.Color.GREEN
                    setCircleColor(android.graphics.Color.GREEN)
                    lineWidth = 2f
                    circleRadius = 4f
                    setDrawValues(false)
                    setDrawFilled(true)
                    fillColor = android.graphics.Color.GREEN
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
                data = LineData(dataSet)
                description = Description().apply { text = "" }
                invalidate()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun SleepInfoCard(title: String, time: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF81C784))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, color = Color.White, fontSize = 16.sp)
            Text(text = time, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SleepScheduleItem(title: String, time: String, remaining: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = "$time - $remaining", fontSize = 14.sp)
            }
            Switch(checked = true, onCheckedChange = {})
        }
    }
}

@Composable
fun SleepScheduleButton() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Horario de sueño diario",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784))
            ) {
                Text(text = "Check", color = Color.White)
            }
        }
    }
}
