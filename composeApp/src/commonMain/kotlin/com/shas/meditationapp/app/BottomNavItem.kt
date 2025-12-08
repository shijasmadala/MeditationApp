package com.shas.meditationapp.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: Route,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Route.HomeScreen, "Home", Icons.Default.Home),
    BottomNavItem(Route.ExploreScreen, "Explore", Icons.Default.Explore),
    BottomNavItem(Route.FavoritesScreen, "Favorites", Icons.Default.Favorite),
    BottomNavItem(Route.ProfileScreen, "Profile", Icons.Default.Person)
)
