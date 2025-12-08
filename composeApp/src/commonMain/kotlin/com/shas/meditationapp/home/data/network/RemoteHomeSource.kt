package com.shas.meditationapp.home.data.network

import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.home.data.dto.HomeTrackResponseDto

interface RemoteHomeSource {
    suspend fun getAllTracks(
        clientId: String,
        formate: String,
        order: String,
        tags: String
    ): Result<HomeTrackResponseDto, DataError.Remote>

    suspend fun getTrendingTrack(
        clientId: String,
        formate: String,
        order: String,
    ): Result<HomeTrackResponseDto, DataError.Remote>
}