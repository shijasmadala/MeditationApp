package com.shas.meditationapp.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * ViewModel store owner for the nested [Route.MainNavGraph] graph so shared
 * ViewModels (e.g. [com.shas.meditationapp.song_details.presentation.SongDetailViewModel])
 * survive when individual destinations are popped.
 *
 * Returns null when [Route.MainNavGraph] is not yet on the back stack (e.g. on the login screen).
 */
@Composable
fun rememberMainNavBackStackEntry(navController: NavController): NavBackStackEntry? {
    val currentEntry by navController.currentBackStackEntryAsState()
    return remember(currentEntry) {
        runCatching { navController.getBackStackEntry<Route.MainNavGraph>() }.getOrNull()
    }
}
