package com.shas.meditationapp.home.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeTrackResponseDto(
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
        @SerialName("results_count")
        val resultsCount: Int?,
        @SerialName("status")
        val status: String?,
        @SerialName("warnings")
        val warnings: String?
    )

    @Serializable
    data class Result(
        @SerialName("album_id")
        val albumId: String?,
        @SerialName("album_image")
        val albumImage: String?,
        @SerialName("album_name")
        val albumName: String?,
        @SerialName("artist_id")
        val artistId: String?,
        @SerialName("artist_idstr")
        val artistIdstr: String?,
        @SerialName("artist_name")
        val artistName: String?,
        @SerialName("audio")
        val audio: String?,
        @SerialName("audiodownload")
        val audiodownload: String?,
        @SerialName("audiodownload_allowed")
        val audiodownloadAllowed: Boolean?,
        @SerialName("content_id_free")
        val contentIdFree: Boolean?,
        @SerialName("duration")
        val duration: Int?,
        @SerialName("id")
        val id: String?,
        @SerialName("image")
        val image: String?,
        @SerialName("license_ccurl")
        val licenseCcurl: String?,
        @SerialName("musicinfo")
        val musicinfo: Musicinfo? = null,
        @SerialName("name")
        val name: String?,
        @SerialName("position")
        val position: Int?,
        @SerialName("prourl")
        val prourl: String?,
        @SerialName("releasedate")
        val releasedate: String?,
        @SerialName("shareurl")
        val shareurl: String?,
        @SerialName("shorturl")
        val shorturl: String?,
        @SerialName("waveform")
        val waveform: String?
    ) {
        @Serializable
        data class Musicinfo(
            @SerialName("acousticelectric")
            val acousticelectric: String?,
            @SerialName("gender")
            val gender: String?,
            @SerialName("lang")
            val lang: String?,
            @SerialName("speed")
            val speed: String?,
            @SerialName("tags")
            val tags: Tags?,
            @SerialName("vocalinstrumental")
            val vocalinstrumental: String?
        ) {
            @Serializable
            data class Tags(
                @SerialName("genres")
                val genres: List<String?>?,
                @SerialName("instruments")
                val instruments: List<String?>?,
                @SerialName("vartags")
                val vartags: List<String?>?
            )
        }
    }
}