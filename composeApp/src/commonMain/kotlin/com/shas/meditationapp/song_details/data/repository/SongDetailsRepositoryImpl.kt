package com.shas.meditationapp.song_details.data.repository

import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.core.domain.map
import com.shas.meditationapp.home.data.network.RemoteHomeSource
import com.shas.meditationapp.home.data.toHomeTrackRespModel
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel
import com.shas.meditationapp.song_details.domain.repository.SongDetailsRepository
import com.shas.meditationapp.util.Constants

class SongDetailsRepositoryImpl(private val remoteHomeSource: RemoteHomeSource) :
    SongDetailsRepository {

    override suspend fun getTrackById(
        trackId: String?,
    ): Result<HomeTrackRespModel, DataError.Remote> {
        return remoteHomeSource.getTrackById(trackId, clientId = Constants.CLIENT_ID)
            .map { it.toHomeTrackRespModel() }
    }
}