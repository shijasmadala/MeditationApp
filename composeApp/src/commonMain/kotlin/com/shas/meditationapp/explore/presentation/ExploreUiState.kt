package com.shas.meditationapp.explore.presentation

import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

data class ExploreUiState(
    val trendingTrack: HomeTrackRespModel? = null,
    val loading: Boolean = false,
    val error: String? = null
)
