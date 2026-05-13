package com.shas.meditationapp.app

import SongDetailsScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.shas.meditationapp.ProfileScreen
import com.shas.meditationapp.explore.presentation.screen.ExploreScreen
import com.shas.meditationapp.explore.presentation.screen.SearchScreen
import com.shas.meditationapp.favorites.FavoritesScreen
import com.shas.meditationapp.home.presentation.screen.HomeScreen
import com.shas.meditationapp.song_details.presentation.MiniPlayerBar
import com.shas.meditationapp.song_details.presentation.SongDetailViewModel
import com.shas.meditationapp.ui.theme.AppBackground
import com.shas.meditationapp.ui.theme.AppTheme
import com.shas.meditationapp.ui.theme.FeaturedCardGradientStart
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            containerColor = AppBackground,
            contentWindowInsets = WindowInsets(top = 0),
            snackbarHost = { SnackbarHost(snackBarHostState) },
            bottomBar = {}
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Route.MainNavGraph,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                navigation<Route.MainNavGraph>(startDestination = Route.HomeScreen) {
                    composable<Route.HomeScreen> {
                        HomeScreen(navController = navController)
                    }

                    composable<Route.ExploreScreen> {
                        ExploreScreen(navController)
                    }

                    composable<Route.FavoritesScreen> {
                        FavoritesScreen()
                    }

                    composable<Route.ProfileScreen> {
                        ProfileScreen()
                    }

                    composable<Route.SongDetailsScreen> {
                        val args = it.toRoute<Route.SongDetailsScreen>()
                        SongDetailsScreen(
                            trackId = args.trackId,
                            navController = navController,
                            snackbarHostState = snackBarHostState
                        )
                    }

                    composable<Route.SearchScreen> {
                        val args = it.toRoute<Route.SearchScreen>()
                        SearchScreen(
                            onBackClick = { navController.navigateUp() },
                            searchQuery = args.searchQuery,
                            navController = navController
                        )
                    }
                }
                }

                val mainNavEntry = rememberMainNavBackStackEntry(navController)
                val songDetailViewModel: SongDetailViewModel =
                    koinViewModel(viewModelStoreOwner = mainNavEntry)
                val showMiniPlayer by songDetailViewModel.showMiniPlayer.collectAsStateWithLifecycle()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val isSongDetailsRoute = currentRoute
                    ?.startsWith(Route.SongDetailsScreen::class.qualifiedName ?: "") == true

                if (showMiniPlayer && !isSongDetailsRoute) {
                    MiniPlayerBar(
                        navController = navController,
                        viewModel = songDetailViewModel
                    )
                }

                NavigationBar(
                    modifier = Modifier.height(100.dp),
                    containerColor = AppBackground
                ) {
                    bottomNavItems.forEach { item ->
                        val routeName = item.route::class.qualifiedName
                        val isSelected = currentRoute == routeName

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(Route.MainNavGraph) { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = FeaturedCardGradientStart,
                                selectedTextColor = FeaturedCardGradientStart,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color(0xFF9A9A9A),
                                unselectedTextColor = Color(0xFF9A9A9A)
                            )
                        )
                    }
                }
            }
        }
    }
}