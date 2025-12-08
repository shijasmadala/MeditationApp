package com.shas.meditationapp.explore.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shas.meditationapp.explore.presentation.screen.components.TrendingTrackTrackScreen
import com.shas.meditationapp.ui.theme.AppBackground
import com.shas.meditationapp.ui.theme.ChipActiveBackground
import com.shas.meditationapp.ui.theme.FeaturedCardGradientEnd
import com.shas.meditationapp.ui.theme.FeaturedCardGradientStart

@Composable
fun ExploreScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            FeaturedCardGradientStart,
                            FeaturedCardGradientEnd
                        )
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(modifier = Modifier.padding(start = 10.dp).statusBarsPadding()) {
                Text("Explore", color = Color.White)

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth().padding(10.dp).height(50.dp),
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = AppBackground.copy(alpha = 0.3f),
                        focusedContainerColor = AppBackground.copy(alpha = 0.3f),
                    ),
                    placeholder = {
                        Text(
                            "Search tracks,artists,playlists",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
            Icon(
                modifier = Modifier.padding(start = 10.dp),
                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = "",
                tint = ChipActiveBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Trending Tracks",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(3) {
                TrendingTrackTrackScreen()
            }
        }
    }
}