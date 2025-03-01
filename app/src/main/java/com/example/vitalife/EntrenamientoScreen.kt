import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBackIosNew
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrenamientoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Entrenamiento Completo",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Retroceder"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Información del entrenamiento
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "32 min", color = Color.Gray)
                    Text(text = "320 Calorías Quemadas", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dificultad
                Text(text = "Dificultad: Principiante", fontSize = 16.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                // Carrusel de Equipos Necesarios
                Text(text = "Necesitarás", fontSize = 18.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))

                val equipos = listOf(
                    R.drawable.pesas to "Pesas",
                    R.drawable.cuerda to "Cuerda de salto",
                    R.drawable.botella to "Botella de agua"
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ejercicios) { ejercicio ->
                        EjercicioItem(ejercicio.first, ejercicio.second, { navController.navigate("descripcion") })
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para ir a la descripción
                Button(
                    onClick = { navController.navigate("cronometro") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Iniciar Entrenamiento", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    )
}

@Composable
fun EquipoCard(imagenRes: Int, nombre: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imagenRes),
                contentDescription = nombre,
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = nombre, fontSize = 14.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun EjercicioItem(
    nombre: String,
    repeticiones: String,
    onClick: () -> Unit // Añade un parámetro para la función onClick
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }, // Aplica el modificador clickable
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = nombre, fontSize = 16.sp)
            Text(text = repeticiones, fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@Preview
@Composable
fun VistaPrevia() {
    val navController = rememberNavController()
    EntrenamientoScreen(navController = navController)
}