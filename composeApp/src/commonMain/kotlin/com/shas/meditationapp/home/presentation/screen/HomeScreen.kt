package com.shas.meditationapp.home.presentation.screen

import SongItemScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.shas.meditationapp.app.Route
import com.shas.meditationapp.home.presentation.HomeViewModel
import com.shas.meditationapp.home.presentation.components.SongChipView
import com.shas.meditationapp.ui.theme.FeaturedCardGradientEnd
import com.shas.meditationapp.ui.theme.FeaturedCardGradientStart
import com.shas.meditationapp.util.UiUtils
import com.shas.meditationapp.util.VerticalHomeShimmer
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(), navController: NavController) {

    val state by viewModel.homeState.collectAsStateWithLifecycle()
    val chipList = listOf("All", "Meditation", "Relaxation")
    var selectedChip by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
                Text("Welcome Back", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Find your inner peace", color = Color.LightGray)
            }
        }

        if (state.loading) VerticalHomeShimmer() else
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                /** Song Chips */
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        LazyRow(modifier = Modifier.padding(10.dp)) {
                            items(chipList) { label ->
                                SongChipView(label, onSelected = {
                                    selectedChip = label
                                    if (it == "All")
                                        viewModel.getAllTracks("relaxing,ambient")
                                    else
                                        viewModel.getAllTracks(it)
                                }, selected = (selectedChip == label))
                            }
                        }
                    }
                }

                /** Featured Today card */
                item {
                    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 0.dp)) {
                        Text("Feature Today", color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            FeaturedCardGradientEnd,
                                            FeaturedCardGradientStart
                                        )
                                    )
                                )
                        ) {

                            // 👉 Transparent image overlay (keeps gradient intact)
                            AsyncImage(
                                model = state.homeTrack?.results?.getOrNull(0)?.albumImage,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .align(Alignment.BottomEnd)
                                    .alpha(0.25f),   // adjust transparency
                                contentScale = ContentScale.Crop
                            )

                            // 👉 Main content goes ABOVE the image
                            Column(
                                modifier = Modifier.padding(start = 20.dp)
                                    .align(Alignment.CenterStart)
                            ) {

                                SongChipView("Most Popular", onSelected = {}, selected = false)

                                val track = state.homeTrack?.results?.getOrNull(0)

                                Text(
                                    text = track?.albumName ?: "Moon Knight Slumber",
                                    color = Color.White
                                )

                                Row {
                                    Text(
                                        text = UiUtils.formatDuration(track?.duration?.toLong() ?: 0),
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "• ${track?.artistName ?: ""}",
                                        color = Color.White
                                    )
                                }
                            }
                        }

                    }
                }

                /** All Sessions title */
                item {
                    Text(
                        "All Sessions",
                        color = Color.White,
                        modifier = Modifier.padding(start = 20.dp, top = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                /** Song list */
                state.homeTrack?.results?.let { tracks ->
                    items(tracks) { track ->
                        SongItemScreen(track, onItemClick = {
                            navController.navigate(Route.SongDetailsScreen(trackId = it?.id ?: ""))
                        })
                    }
                }
            }
    }
}

