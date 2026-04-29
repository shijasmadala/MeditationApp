package com.shas.meditationapp.explore.data.repository

import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.core.domain.map
import com.shas.meditationapp.explore.data.toAlbumModel
import com.shas.meditationapp.explore.domain.model.AlbumResponseModel
import com.shas.meditationapp.explore.domain.repository.ExploreRepository
import com.shas.meditationapp.home.data.network.RemoteHomeSource
import com.shas.meditationapp.home.data.toHomeTrackRespModel
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel
import com.shas.meditationapp.util.Constants

class ExploreRepositoryImpl(private val remoteHomeSource: RemoteHomeSource) : ExploreRepository {
    override suspend fun getPopularAlbums(
        order: String,
        limit: String
    ): Result<AlbumResponseModel, DataError.Remote> {
        return remoteHomeSource.getPopularAlbums(
            clientId = Constants.CLIENT_ID,
            formate = "jsonpretty",
            order = order,
            limit = limit
        ).map {
            it.toAlbumModel()
        }
    }

    override suspend fun searchTrackByNameAndArtist(
        nameSearch: String,
        artistName: String
    ): Result<HomeTrackRespModel, DataError.Remote> {
        return remoteHomeSource.searchTrackByNameAndArtist(
            clientId = Constants.CLIENT_ID,
            formate = "jsonpretty",
            nameSearch = nameSearch,
            artistName = artistName
        ).map { it.toHomeTrackRespModel() }
    }
}