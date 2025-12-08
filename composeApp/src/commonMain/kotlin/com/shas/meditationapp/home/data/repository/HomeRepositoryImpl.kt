package com.shas.meditationapp.home.data.repository

import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.core.domain.map
import com.shas.meditationapp.home.data.network.RemoteHomeSource
import com.shas.meditationapp.home.data.toHomeTrackRespModel
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel
import com.shas.meditationapp.home.domain.repository.HomeRepository

class HomeRepositoryImpl(private val remoteHomeSource: RemoteHomeSource) : HomeRepository {
    override suspend fun getAllTracks(
        clientId: String,
        formate: String,
        order: String,
        tags: String
    ): Result<HomeTrackRespModel, DataError.Remote> {
        return remoteHomeSource.getAllTracks(
            clientId = clientId,
            formate = formate,
            order = order,
            tags
        ).map {
            it.toHomeTrackRespModel()
        }
    }

    override suspend fun getTrendingTrack(
        clientId: String,
        formate: String,
        order: String
    ): Result<HomeTrackRespModel, DataError.Remote> {
        return remoteHomeSource.getTrendingTrack(clientId, formate, order).map {
            it.toHomeTrackRespModel()
        }
    }

}