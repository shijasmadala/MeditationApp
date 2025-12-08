package com.shas.meditationapp.home.domain.repository

import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

interface HomeRepository {
    suspend fun getAllTracks(
        clientId: String,
        formate: String,
        order: String,
        tags: String
    ): Result<HomeTrackRespModel, DataError.Remote>

    suspend fun getTrendingTrack(
        clientId: String,
        formate: String,
        order: String,
    ): Result<HomeTrackRespModel, DataError.Remote>
}