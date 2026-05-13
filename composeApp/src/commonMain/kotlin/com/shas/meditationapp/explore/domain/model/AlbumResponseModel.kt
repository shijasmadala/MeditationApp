package com.shas.meditationapp.explore.domain.model

import com.shas.meditationapp.home.domain.model.HeaderModel

data class AlbumResponseModel(
    val headers: HeaderModel?,
    val results: List<AlbumResultModel?>?
)

data class AlbumResultModel(
    val artistId: String?,
    val artistName: String?,
    val id: String?,
    val image: String?,
    val name: String?,
    val releaseDate: String?,
    val shareUrl: String?,
    val shortUrl: String?,
    val zip: String?,
    val zipAllowed: Boolean?
)
