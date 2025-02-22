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
import com.example.vitalife.ui.screens.*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitalifeTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
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
        composable("sleepTracking") { SleepTrackerScreen(onCheckClick = { navController.navigate("schedule") }) }
        composable("workoutTracker") { WorkoutTrackerScreen(navController) }
        composable("schedule") { ScheduleScreen(navController) }
        composable("addSchedule") { AddScheduleScreen(navController) }

        // Agregadas desde la rama Leonardo
        composable("home") { HomeScreen(navController) }
        composable("notifications") { NotificationsScreen() }
        composable("auth") { GoogleFitAuthScreenWrapper(navController) }
        composable("chatbot") { ChatBotScreen(navController) }
    }
}

@Composable
fun GoogleFitAuthScreenWrapper(navController: NavHostController) {
    var account by remember { mutableStateOf<GoogleSignInAccount?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleFitAuthScreen(context = context) { signedInAccount ->
            account = signedInAccount
        }

        Spacer(modifier = Modifier.height(20.dp))

        account?.let {
            Text("Bienvenido, ${it.displayName ?: "Usuario"}", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text("Ir a Home")
        }
    }
}
