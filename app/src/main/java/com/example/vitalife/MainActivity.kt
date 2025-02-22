package com.example.vitalife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vitalife.ui.theme.VitalifeTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            VitalifeTheme {
                SleepApp()
                //val navController = rememberNavController()
               // AppNavHost(navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
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
            ProfileScreen(navController, userId)
        }
        composable("sleepTracking") { SleepTrackerScreen(onCheckClick = {navController.navigate("schedule")}) }
        composable("workoutTracker") { WorkoutTrackerScreen(navController) }
        composable("schedule") { ScheduleScreen(navController) }
        composable("addSchedule") { AddScheduleScreen(navController) } // ✅ Nueva pantalla añadida

        
    }
}
