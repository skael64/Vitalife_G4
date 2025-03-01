package com.example.vitalife.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Sms
import com.example.vitalife.model.NavItem

object NavItemList {
    val navItemList = listOf(
        NavItem("Perfil", Icons.Rounded.Person),
        NavItem("Inicio", Icons.Rounded.Home),
        NavItem("Chatbot", Icons.Rounded.Sms)
    )
}