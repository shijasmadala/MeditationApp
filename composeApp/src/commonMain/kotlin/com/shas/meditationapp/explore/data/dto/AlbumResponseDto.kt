package com.shas.meditationapp.explore.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumResponseDto(
    @SerialName("headers")
    val headers: Headers?,
    @SerialName("results")
    val results: List<Result?>?
) {
    @Serializable
    data class Headers(
        @SerialName("code")
        val code: Int?,
        @SerialName("error_message")
        val errorMessage: String?,
        @SerialName("next")
        val next: String? = null,
        @SerialName("results_count")
        val resultsCount: Int?,
        @SerialName("status")
        val status: String?,
        @SerialName("warnings")
        val warnings: String?
    )

    @Serializable
    data class Result(
        @SerialName("artist_id")
        val artistId: String?,
        @SerialName("artist_name")
        val artistName: String?,
        @SerialName("id")
        val id: String?,
        @SerialName("image")
        val image: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("releasedate")
        val releasedate: String?,
        @SerialName("shareurl")
        val shareurl: String?,
        @SerialName("shorturl")
        val shorturl: String?,
        @SerialName("zip")
        val zip: String?,
        @SerialName("zip_allowed")
        val zipAllowed: Boolean?
    )
}