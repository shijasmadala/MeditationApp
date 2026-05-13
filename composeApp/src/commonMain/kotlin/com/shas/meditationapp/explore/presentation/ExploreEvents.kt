package com.shas.meditationapp.explore.presentation

sealed class ExploreEvents {
    data class OnSearch(val query: String) : ExploreEvents()
}