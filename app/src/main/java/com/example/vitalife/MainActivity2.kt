package com.example.vitalife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vitalife.ui.screens.GoogleFitAuthScreen
import com.example.vitalife.ui.screens.HomeScreen
import com.example.vitalife.ui.screens.NotificationsScreen
import com.example.vitalife.ui.screens.ChatBotScreen
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
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("notifications") { NotificationsScreen() }
                composable("auth") { GoogleFitAuthScreenWrapper(navController) }
                composable("chatbot") { ChatBotScreen(navController) }

            }
        }
    }
}

@Composable
fun GoogleFitAuthScreenWrapper(navController: androidx.navigation.NavController) {
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
