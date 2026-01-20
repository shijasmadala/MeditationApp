package com.shas.meditationapp

import androidx.compose.ui.window.ComposeUIViewController
import com.shas.meditationapp.di.initKoin
import com.shas.meditationapp.app.App

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }