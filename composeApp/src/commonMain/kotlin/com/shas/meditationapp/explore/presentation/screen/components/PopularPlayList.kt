package com.shas.meditationapp.explore.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import meditationapp.composeapp.generated.resources.Res
import meditationapp.composeapp.generated.resources.nature
import org.jetbrains.compose.resources.painterResource

@Composable
fun PopularPlayListScreen() {
    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.Start) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Gray.copy(alpha = 0.1f))
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.nature),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            "Midnight Meditation",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            "Poet",
            color = Color.LightGray,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}