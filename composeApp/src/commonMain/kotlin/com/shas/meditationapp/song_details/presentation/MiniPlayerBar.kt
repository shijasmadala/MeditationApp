package com.shas.meditationapp.song_details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.shas.meditationapp.app.Route
import com.shas.meditationapp.app.rememberMainNavBackStackEntry
import com.shas.meditationapp.ui.theme.AppBackground
import org.koin.compose.viewmodel.koinViewModel

private val MiniPlayerPurple = Color(0xFF8A2BE2)

@Composable
fun MiniPlayerBar(
    navController: NavController,
    viewModel: SongDetailViewModel = koinViewModel(
        viewModelStoreOwner = rememberMainNavBackStackEntry(navController)
    )
) {
    val state by viewModel.songDetailState.collectAsStateWithLifecycle()
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val isBuffering by viewModel.isBuffering.collectAsStateWithLifecycle()
    val track = state.songDetail?.results?.firstOrNull()
    val title = track?.name.orEmpty()
    val imageUrl = track?.image
    val audioUrl = track?.audio
    val trackId = state.activeTrackId.orEmpty()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(Color(0xFF2A2A2A))
            .clickable(enabled = trackId.isNotBlank()) {
                navController.navigate(Route.SongDetailsScreen(trackId = trackId))
            }
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(enabled = trackId.isNotBlank()) {
                    navController.navigate(Route.SongDetailsScreen(trackId = trackId))
                }
        ) {
            Text(
                text = title.ifBlank { "Now playing" },
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Meditation",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MiniPlayerPurple)
                .clickable {
                    if (isPlaying) viewModel.pause() else {
                        if (!audioUrl.isNullOrBlank()) viewModel.play(audioUrl)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (isBuffering) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = AppBackground,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        IconButton(onClick = { viewModel.dismissMiniPlayer() }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Stop and dismiss",
                tint = Color.LightGray
            )
        }
    }
}
