package com.shas.meditationapp.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import com.shas.meditationapp.IosAudioPlayer
import com.shas.meditationapp.auth.domain.GoogleAuthManager
import com.shas.meditationapp.song_details.domain.AudioPlayer
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<AudioPlayer> { IosAudioPlayer() }
        single { GoogleAuthManager() }
        single<Settings> {
            NSUserDefaultsSettings(
                NSUserDefaults.standardUserDefaults
            )
        }
    }