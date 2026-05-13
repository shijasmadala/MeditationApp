package com.shas.meditationapp.song_details.domain.repository

import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

interface SongDetailsRepository {
    suspend fun getTrackById(
        trackId: String?,
    ): Result<HomeTrackRespModel, DataError.Remote>
}