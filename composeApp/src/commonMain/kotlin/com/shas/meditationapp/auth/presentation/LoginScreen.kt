package com.shas.meditationapp.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mediation
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Brightness3
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shas.meditationapp.app.Route
import com.shas.meditationapp.home.presentation.components.FeatureChip
import com.shas.meditationapp.ui.theme.AppBackground
import meditationapp.composeapp.generated.resources.Res
import meditationapp.composeapp.generated.resources.google_login
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = koinViewModel()) {
    val state by viewModel.authState.collectAsStateWithLifecycle()
    LaunchedEffect(state.userData) {
        if (state.userData != null) {
            navController.navigate(Route.HomeScreen) {
                popUpTo(Route.LoginScreen) {
                    inclusive = true
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))

            // App Logo
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFA855F7),
                                Color(0xFF3B82F6)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(52.dp)
                )
            }
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Serenity",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFA78BFA)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(text = "Your peaceful escape", fontSize = 18.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(34.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FeatureChip(text = "Meditation", icon = Icons.Default.Mediation)
                FeatureChip(text = "Sleep", icon = Icons.Outlined.Brightness3)
            }
            Spacer(modifier = Modifier.height(18.dp))
            FeatureChip(text = "Relaxation", icon = Icons.Outlined.AcUnit)

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = {
                    viewModel.signIn()
                },
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth().heightIn(58.dp),
                border = BorderStroke(
                    1.dp,
                    Color.White
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                )
            ) {
                Image(
                    painter = painterResource(Res.drawable.google_login),
                    contentDescription = "",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Continue with google", color = Color.White)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("or", color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(Route.HomeScreen)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(
                    1.dp,
                    Color(0xFF5B21B6)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFA855F7)
                )
            ) {

                Text(
                    text = "Continue as Guest",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
        }
    }
}
