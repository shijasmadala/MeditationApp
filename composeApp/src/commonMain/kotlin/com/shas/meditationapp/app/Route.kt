package com.shas.meditationapp.app

import kotlinx.serialization.Serializable

interface Route {
    @Serializable
    data object MainNavGraph : Route

    @Serializable
    data object HomeScreen : Route

    @Serializable
    data object ExploreScreen : Route

    @Serializable
    data object FavoritesScreen : Route

    @Serializable
    data object ProfileScreen : Route

    @Serializable
    data class SongDetailsScreen(val trackId: String) : Route

}