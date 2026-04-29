package com.shas.meditationapp.home.data.network

import com.shas.meditationapp.core.data.safeCall
import com.shas.meditationapp.core.domain.DataError
import com.shas.meditationapp.core.domain.Result
import com.shas.meditationapp.explore.data.dto.AlbumResponseDto
import com.shas.meditationapp.home.data.dto.HomeTrackResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.jamendo.com/v3.0"

class KtorRemoteHomeSource(private val httpClient: HttpClient) : RemoteHomeSource {
    override suspend fun getAllTracks(
        clientId: String,
        formate: String,
        order: String,
        tags: String
    ): Result<HomeTrackResponseDto, DataError.Remote> {
        return safeCall<HomeTrackResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/tracks/?"
            ) {
                parameter("client_id", clientId)
                parameter("format", formate)
                parameter("order", order)
                parameter("tags", tags)
                parameter("limit", "20")
            }
        }
    }

    override suspend fun getTrendingTrack(
        clientId: String,
        formate: String,
        order: String
    ): Result<HomeTrackResponseDto, DataError.Remote> {
        return safeCall<HomeTrackResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/tracks/?"
            ) {
                parameter("client_id", clientId)
                parameter("format", formate)
                parameter("order", order)
            }
        }
    }

    override suspend fun getPopularAlbums(
        clientId: String,
        formate: String,
        order: String,
        limit: String
    ): Result<AlbumResponseDto, DataError.Remote> {
        return safeCall<AlbumResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/albums/?"
            ) {
                parameter("limit", limit)
                parameter("client_id", clientId)
                parameter("format", formate)
                parameter("order", order)
                parameter("tags", "relaxing")
            }
        }
    }

    override suspend fun getTrackById(
        trackId: String?,
        clientId: String
    ): Result<HomeTrackResponseDto, DataError.Remote> {
        return safeCall<HomeTrackResponseDto> {
            httpClient.get(urlString = "$BASE_URL/tracks/?") {
                parameter("id", trackId)
                parameter("client_id", clientId)
            }
        }
    }

    override suspend fun searchTrackByNameAndArtist(
        clientId: String,
        formate: String,
        nameSearch: String,
    ): Result<HomeTrackResponseDto, DataError.Remote> {
        return safeCall<HomeTrackResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/tracks/?"
            ) {
                parameter("client_id", clientId)
                parameter("format", formate)
                parameter("namesearch", nameSearch)
                parameter("limit", "20")
            }
        }
    }

}