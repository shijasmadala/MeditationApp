package com.shas.meditationapp.di

import com.shas.meditationapp.IosAudioPlayer
import com.shas.meditationapp.song_details.domain.AudioPlayer
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<AudioPlayer> { IosAudioPlayer() }
    }