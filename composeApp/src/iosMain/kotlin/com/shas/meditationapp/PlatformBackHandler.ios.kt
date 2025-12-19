package com.shas.meditationapp

import androidx.compose.runtime.Composable


@Composable
actual fun PlatformBackHandler(
    onBack: () -> Unit
) {
    // iOS uses swipe-back / navigation controller
    // Hook later if needed
}