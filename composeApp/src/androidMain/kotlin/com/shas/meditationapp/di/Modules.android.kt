package com.shas.meditationapp.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.shas.meditationapp.AndroidAudioPlayer
import com.shas.meditationapp.auth.domain.GoogleAuthManager
import com.shas.meditationapp.song_details.domain.AudioPlayer
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<AudioPlayer> { AndroidAudioPlayer(androidContext()) }
        single {
            GoogleAuthManager(androidContext())
        }
        single<Settings> {

            SharedPreferencesSettings(
                androidContext().getSharedPreferences(
                    "app_settings",
                    Context.MODE_PRIVATE
                )
            )
        }
    }