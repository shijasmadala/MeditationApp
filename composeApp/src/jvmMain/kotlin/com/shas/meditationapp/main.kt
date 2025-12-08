package com.shas.meditationapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.shas.meditationapp.app.App
import com.shas.meditationapp.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "MeditationApp",
    ) {
        App()
    }
}