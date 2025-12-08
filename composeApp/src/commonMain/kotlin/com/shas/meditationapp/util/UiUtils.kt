package com.shas.meditationapp.util

object UiUtils {
    fun formatDuration(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return minutes.toString() + ":" + remainingSeconds.toString().padStart(2, '0')
    }
}