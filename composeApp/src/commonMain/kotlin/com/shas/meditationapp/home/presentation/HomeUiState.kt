package com.shas.meditationapp.home.presentation

import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

data class HomeUiState(
    val homeTrack: HomeTrackRespModel? = null,
    val loading: Boolean = false,
    val error: String? = null
)