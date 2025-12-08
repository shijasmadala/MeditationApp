package com.shas.meditationapp.explore.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import meditationapp.composeapp.generated.resources.Res
import meditationapp.composeapp.generated.resources.nature
import org.jetbrains.compose.resources.painterResource

@Composable
fun TrendingTrackTrackScreen() {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.Gray.copy(alpha = 0.1f))
    ) {
        Image(painter = painterResource(resource = Res.drawable.nature), contentDescription = "")
    }
}