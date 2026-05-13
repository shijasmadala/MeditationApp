package com.shas.meditationapp.util

import androidx.compose.ui.graphics.Color

object UiUtils {
    fun formatDuration(seconds: Long): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return minutes.toString() + ":" + remainingSeconds.toString().padStart(2, '0')
    }

    fun formatTime(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "$minutes:${seconds.toString().padStart(2, '0')}"
    }

    data class Genre(
        val emoji: String,
        val title: String,
        val subtitle: String,
        val gradient: List<Color>
    )

    val genres = listOf(
        Genre(
            "🧘", "Meditation", "250+ tracks",
            listOf(Color(0xFF3B0E86), Color(0xFF6A0DAD))
        ),
        Genre(
            "😴", "Sleep", "180+ tracks",
            listOf(Color(0xFF1B2A49), Color(0xFF3C2A72))
        ),
        Genre(
            "🌿", "Nature", "320+ tracks",
            listOf(Color(0xFF003A1C), Color(0xFF012F1B))
        ),
        Genre(
            "🧘‍♀️", "Yoga", "150+ tracks",
            listOf(Color(0xFF4C0C0C), Color(0xFF8B1A1A))
        ),
        Genre(
            "🎯", "Focus", "200+ tracks",
            listOf(Color(0xFF002E6E), Color(0xFF003F8E))
        ),
        Genre(
            "😇", "Relaxation", "210+ tracks",
            listOf(Color(0xFF3C0D53), Color(0xFF6D0F79))
        )
    )


}