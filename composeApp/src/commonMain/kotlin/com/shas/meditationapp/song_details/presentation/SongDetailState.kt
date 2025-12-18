package com.shas.meditationapp.song_details.presentation

import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

data class SongDetailState(
    val songDetail: HomeTrackRespModel? = null,
    val loading: Boolean = false,
    val error: String? = null
)
