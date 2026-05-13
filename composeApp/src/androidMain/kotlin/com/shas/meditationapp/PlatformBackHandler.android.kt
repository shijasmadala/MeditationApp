package com.shas.meditationapp

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun PlatformBackHandler(
    onBack: () -> Unit
) {
    BackHandler(enabled = true) {
        onBack()
    }
}