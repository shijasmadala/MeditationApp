package com.shas.meditationapp.explore.domain.repository

import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.explore.domain.model.AlbumResponseModel
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel

interface ExploreRepository {

    suspend fun getPopularAlbums(
        order: String,
        limit: String
    ): Result<AlbumResponseModel, DataError.Remote>

    suspend fun searchTrackByNameAndArtist(
        nameSearch: String,
    ): Result<HomeTrackRespModel, DataError.Remote>
}