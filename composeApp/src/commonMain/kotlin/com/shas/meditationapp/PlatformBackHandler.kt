package com.shas.meditationapp

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformBackHandler(
    onBack: () -> Unit
)