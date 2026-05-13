package com.shas.meditationapp.explore.data

import com.shas.meditationapp.explore.data.dto.AlbumResponseDto
import com.shas.meditationapp.explore.domain.model.AlbumResponseModel
import com.shas.meditationapp.explore.domain.model.AlbumResultModel
import com.shas.meditationapp.home.domain.model.HeaderModel

fun AlbumResponseDto.toAlbumModel(): AlbumResponseModel {
    return AlbumResponseModel(
        headers = headers?.toHeaderModel(),
        results = results?.map {
            it
                ?.toAlbumResultModel()
        }
    )
}

fun AlbumResponseDto.Result.toAlbumResultModel(): AlbumResultModel {
    return AlbumResultModel(
        artistId = artistId,
        artistName = artistName,
        id = id,
        image = image,
        name = name,
        releaseDate = releasedate,
        shareUrl = shareurl,
        shortUrl = shareurl,
        zip = zip,
        zipAllowed = zipAllowed
    )
}

fun AlbumResponseDto.Headers.toHeaderModel(): HeaderModel {
    return HeaderModel(
        code = code,
        errorMessage = errorMessage,
        resultsCount = resultsCount,
        status = status,
        warnings = warnings
    )
}