package com.example.vitalife
/*
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import androidx.navigation.compose.rememberNavController

@Composable
fun GoogleFitAuthScreen(context: Context, onSignedIn: (GoogleSignInAccount) -> Unit) {
    val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
        .build()

    val googleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .addExtension(fitnessOptions)
            .build()
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            account?.let { onSignedIn(it) }
        }
    }

    Button(onClick = {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent) // Ahora se usa el intent directamente
    }) {
        Text("Conectar con Google Fit")
    }
}

@Preview
@Composable
fun VistaPreviaGoogleFitAuthScreen() {
    val navController = rememberNavController()
    GoogleFitAuthScreen(navController = navController)
}

 */