package com.shas.meditationapp.explore.presentation

import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

data class SearchScreenState(
    val searchedTrack: HomeTrackRespModel? = null,
    val loading: Boolean = false,
    val error: String? = null
)