package com.example.vitalife

import EntrenamientoScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vitalife.navigation.BottomNavBar
import com.example.vitalife.navigation.NavItemList
import com.example.vitalife.ui.theme.VitalifeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VitalifeTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(0) } // Índice seleccionado en el NavBar

    Scaffold(
        bottomBar = {
            // Mostrar el NavBar solo en las pantallas principales
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute in listOf("home", "profile", "chat")) {
                BottomNavBar(
                    navItemList = NavItemList.navItemList,
                    selectedIndex = selectedIndex,
                    onItemSelected = { index ->
                        selectedIndex = index
                        when (index) {
                            0 -> navController.navigate("profile")
                            1 -> navController.navigate("home")
                            2 -> navController.navigate("chat")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash") { SplashScreen(navController) }
            composable("onboarding") { OnboardingScreen(navController) }
            composable("register") { RegisterScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("welcome/{userName}/{userId}") { backStackEntry ->
                val userName = backStackEntry.arguments?.getString("userName") ?: "Usuario"
                val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                WelcomeScreen(navController, userName, userId)
            }
            composable("profile/{userId}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
                ProfileScreen(navController)
            }
            composable("profile") { ProfileScreen(navController) }
            composable("home") { HomeScreen(navController) }
            composable("chat") { ChatBotScreen(navController) }
            composable("notifications") { NotificationsScreen(navController) }
            composable("sleepTracking") { SleepTrackerScreen(navController) }
            composable("sleepSchedule") { SleepScheduleScreen(navController) }
            composable("addAlarm") { AddAlarmScreen(navController) }

            composable("workoutTracker") { WorkoutTrackerScreen(navController) }
            composable("workoutSchedule") { WorkoutScheduleScreen(navController) }
            composable("addSchedule") { AddScheduleScreen(navController) }
            composable("entrenamiento") { EntrenamientoScreen(navController) }
            composable("descripcion") { DescripcionScreen(navController, "Salto de tijera" ) }
            composable("cronometro") { CronometroScreen(navController, "Salto de tijera") }
            composable("mensaje") { MensajeFinalScreen(navController) }
        }
    }
}