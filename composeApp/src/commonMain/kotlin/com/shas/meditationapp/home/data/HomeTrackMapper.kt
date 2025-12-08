package com.shas.meditationapp.home.data

import com.shas.meditationapp.home.data.dto.HomeTrackResponseDto
import com.shas.meditationapp.home.domain.model.HeaderModel
import com.shas.meditationapp.home.domain.model.HomeTrackRespModel
import com.shas.meditationapp.home.domain.model.ResultModel

fun HomeTrackResponseDto.toHomeTrackRespModel(): HomeTrackRespModel {
    return HomeTrackRespModel(
        headers = headers?.toHeaderModel(), results = results?.map { it?.toResultModel() })
}

fun HomeTrackResponseDto.Headers.toHeaderModel(): HeaderModel {
    return HeaderModel(
        code = code,
        errorMessage = errorMessage,
        resultsCount = resultsCount,
        status = status,
        warnings = warnings
    )
}

fun HomeTrackResponseDto.Result.toResultModel(): ResultModel {
    return ResultModel(
        albumId = albumId,
        albumImage = albumImage,
        artistId = artistId,
        albumName = albumName,
        artistInstr = artistIdstr,
        artistName = artistName,
        audio = audio,
        audioDownload = audiodownload,
        audioDownloadAllowed = audiodownloadAllowed,
        contentIdFree = contentIdFree,
        duration = duration,
        id = id,
        image = image,
        licenseCurl = licenseCcurl,
        name = name,
        position = position,
        prourl = prourl,
        releaseDate = releasedate,
        shareurl = shareurl,
        shorturl = shorturl,
        waveform = waveform
    )
}