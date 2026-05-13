package com.shas.meditationapp.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.shas.meditationapp.app.Route

@Composable
fun LoginScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = "Login Screen", color = Color.White, modifier = Modifier.clickable {
            navController.navigate(Route.HomeScreen)
        })
    }
}
