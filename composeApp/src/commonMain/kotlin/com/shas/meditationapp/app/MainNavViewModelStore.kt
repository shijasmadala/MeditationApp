package com.shas.meditationapp.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**
 * ViewModel store owner for the nested [Route.MainNavGraph] graph so shared
 * ViewModels (e.g. [com.shas.meditationapp.song_details.presentation.SongDetailViewModel])
 * survive when individual destinations are popped.
 */
@Composable
fun rememberMainNavBackStackEntry(navController: NavController): NavBackStackEntry {
    return remember(navController) {
        navController.getBackStackEntry<Route.MainNavGraph>()
    }
}
