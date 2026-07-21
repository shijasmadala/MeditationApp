import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.shas.meditationapp.PlatformBackHandler
import com.shas.meditationapp.app.rememberMainNavBackStackEntry
import com.shas.meditationapp.home.domain.model.ResultModel
import com.shas.meditationapp.song_details.presentation.SongDetailViewModel
import com.shas.meditationapp.ui.theme.AppBackground
import com.shas.meditationapp.util.UiUtils
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SongDetailsScreen(
    navController: NavController,
    viewModel: SongDetailViewModel = koinViewModel(
        viewModelStoreOwner = rememberMainNavBackStackEntry(navController)!!
    ),
    trackId: String?,
    snackBarHostState: SnackbarHostState
) {
    val state by viewModel.songDetailState.collectAsStateWithLifecycle()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val position by viewModel.position.collectAsState()
    val remaining by viewModel.songTimer.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val isSongBuffering by viewModel.isBuffering.collectAsState()

    PlatformBackHandler {
        navController.popBackStack()
    }

    var sliderPosition by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var songItem: ResultModel? = null

    LaunchedEffect(position, duration, isDragging) {
        if (!isDragging && duration > 0) {
            sliderPosition = position.toFloat()
        }
    }

    if (state.songDetail?.results?.isNotEmpty() == true) {
        songItem = state.songDetail?.results?.get(0)!!
    }

    LaunchedEffect(trackId) {
        viewModel.getTrackById(trackId)
    }

    LaunchedEffect(state.loading, state.songDetail) {
        if (!state.loading &&
            state.songDetail != null &&
            state.songDetail?.results.isNullOrEmpty()
        ) {
            snackBarHostState.showSnackbar(
                "Track currently not available",
                duration = SnackbarDuration.Short
            )
            navController.navigateUp()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {

        // 🔝 Top Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Now Playing",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppBackground,
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.Unspecified,
                titleContentColor = Color.Unspecified,
                actionIconContentColor = Color.Unspecified
            )
        )

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(24.dp))

            // 🖼 Album Art
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.DarkGray)
            ) {
                AsyncImage(
                    model = songItem?.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🎵 Title
            Text(
                text = songItem?.name ?: "",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 🧘 Subtitle
            Text(
                text = "Meditation Session",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 📊 Plays + Duration
            Text(
                text = "${songItem?.artistName.toString()}  •  ${UiUtils.formatDuration(songItem?.duration?.toLong() ?: 0)}",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ⏳ Slider
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        isDragging = true
                        sliderPosition = it
                    },
                    onValueChangeFinished = {
                        isDragging = false
                        viewModel.seekTo(sliderPosition.toLong())
                    },
                    valueRange = 0f..duration.toFloat(),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.DarkGray
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(UiUtils.formatTime(elapsedTime), color = Color.Gray, fontSize = 12.sp)
                    Text(
                        UiUtils.formatTime(remaining),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ▶ Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { }, colors = IconButtonColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.3f),
                        contentColor = Color.Unspecified,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(26.dp)
                    )
                }

                val audioUrl = state.songDetail
                    ?.results
                    ?.firstOrNull()
                    ?.audio
                // Play button
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8A2BE2))
                        .clickable {
                            if (isPlaying) viewModel.pause() else {
                                if (!audioUrl.isNullOrBlank()) {
                                    viewModel.play(audioUrl)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isSongBuffering)
                        CircularProgressIndicator(color = AppBackground)
                    else
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                }

                IconButton(
                    onClick = { }, colors = IconButtonColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.3f),
                        contentColor = Color.Unspecified,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Download,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            //description
            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
                    .fillMaxWidth().height(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray.copy(alpha = 0.6f)).padding(8.dp)
            ) {
                Column {
                    Text(
                        "About this track",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyMedium,
                        text = "This meditation session is designed to help you find peace and tranquility. Perfect for daily meditation practice. Close your eyes, breathe deeply, and let the soothing sounds guide you to a state of calm."
                    )
                }
            }
        }
    }

}
