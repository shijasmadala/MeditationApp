package com.shas.meditationapp.explore.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.GeneratingTokens
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shas.meditationapp.app.Route
import com.shas.meditationapp.explore.presentation.ExploreViewModel
import com.shas.meditationapp.explore.presentation.screen.components.GenresCardsView
import com.shas.meditationapp.explore.presentation.screen.components.NewReleasesView
import com.shas.meditationapp.explore.presentation.screen.components.PopularPlayListScreen
import com.shas.meditationapp.explore.presentation.screen.components.TrendingTrackTrackScreen
import com.shas.meditationapp.ui.theme.AppBackground
import com.shas.meditationapp.ui.theme.ChipActiveBackground
import com.shas.meditationapp.ui.theme.FeaturedCardGradientEnd
import com.shas.meditationapp.ui.theme.FeaturedCardGradientStart
import com.shas.meditationapp.util.HorizontalItemShimmer
import com.shas.meditationapp.util.UiUtils.genres
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExploreScreen(navController: NavController, viewModel: ExploreViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(10.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            FeaturedCardGradientStart, FeaturedCardGradientEnd
                        )
                    )
                ), contentAlignment = Alignment.CenterStart
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
                    }, trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            modifier = Modifier.clickable(onClick = {
                                navController.navigate(Route.SearchScreen)
                            })
                        )
                    })
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        TitleComposable(name = "Trending Tracks", icon = Icons.AutoMirrored.Filled.TrendingUp)
        Spacer(modifier = Modifier.height(8.dp))

        if (state.loading) {
            LazyRow {
                items(5) { HorizontalItemShimmer() }
            }
        } else {
            LazyRow {
                state.trendingTrack?.results?.let { items ->
                    items(items) {
                        TrendingTrackTrackScreen(it, onItemClick = { item ->
                            navController.navigate(Route.SongDetailsScreen(item?.id ?: ""))
                        })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        TitleComposable(name = "New Releases", icon = Icons.Default.NewReleases)
        Spacer(modifier = Modifier.height(8.dp))
        if (state.loading) {
            LazyRow {
                items(5) { HorizontalItemShimmer() }
            }
        } else {
            LazyRow {
                state.popularAlbum?.results?.let { albums ->
                    items(albums) {
                        NewReleasesView(it, onItemClick = { item ->
                            navController.navigate(Route.SongDetailsScreen(item?.id ?: ""))
                        })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        TitleComposable(name = "Popular Playlists", icon = Icons.Default.MusicNote)
        Spacer(modifier = Modifier.height(8.dp))

        if (state.loading) {
            LazyRow {
                items(5) { HorizontalItemShimmer() }
            }
        } else {
            LazyRow {
                state.popularPlayList?.results?.let { playList ->
                    items(items = playList) { item ->
                        PopularPlayListScreen(item, onItemClick = { data ->
                            navController.navigate(Route.SongDetailsScreen(data?.id ?: ""))
                        })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        TitleComposable(name = "Brows Genres", icon = Icons.Default.GeneratingTokens)
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(genres) {
                GenresCardsView(
                    emoji = it.emoji,
                    title = it.title,
                    subtitle = it.subtitle,
                    gradient = it.gradient
                )
            }
        }
    }
}


@Composable
fun TitleComposable(name: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
        Icon(
            modifier = Modifier.padding(start = 10.dp),
            imageVector = icon,
            contentDescription = "",
            tint = ChipActiveBackground
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            name, color = Color.White, style = MaterialTheme.typography.bodyMedium
        )
    }
}