package com.shas.meditationapp.home.domain.model


data class HomeTrackRespModel(
    val headers: HeaderModel?,
    val results: List<ResultModel?>?
)

data class HeaderModel(
    val code: Int?,
    val errorMessage: String?,
    val resultsCount: Int?,
    val status: String?,
    val warnings: String?
)

data class ResultModel(
    val albumId: String?,
    val albumImage: String?,
    val albumName: String?,
    val artistId: String?,
    val artistInstr: String?,
    val artistName: String?,
    val audio: String?,
    val audioDownload: String?,
    val audioDownloadAllowed: Boolean?,
    val contentIdFree: Boolean?,
    val duration: Int?,
    val id: String?,
    val image: String?,
    val licenseCurl: String?,
    val name: String?,
    val position: Int?,
    val prourl: String?,
    val releaseDate: String?,
    val shareurl: String?,
    val shorturl: String?,
    val waveform: String?
)