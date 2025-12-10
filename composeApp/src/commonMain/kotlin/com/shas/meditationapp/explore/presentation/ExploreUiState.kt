package com.shas.meditationapp.explore.presentation

import com.shas.meditationapp.explore.domain.model.AlbumResponseModel
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

data class ExploreUiState(
    val trendingTrack: HomeTrackRespModel? = null,
    val popularAlbum: AlbumResponseModel? = null,
    val popularPlayList: AlbumResponseModel? = null,
    val loading: Boolean = false,
    val error: String? = null
)
