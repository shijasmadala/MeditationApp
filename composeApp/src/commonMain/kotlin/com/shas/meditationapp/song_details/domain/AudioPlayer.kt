package com.shas.meditationapp.song_details.domain

import kotlinx.coroutines.flow.StateFlow

interface AudioPlayer {
    fun play(url: String)
    fun pause()
    fun stop()
    fun seekTo(positionMs: Long)
    fun release()
    fun reset()

    val isPlaying: StateFlow<Boolean>
    val currentPosition: StateFlow<Long>
    val duration: StateFlow<Long>
    val isBuffering: StateFlow<Boolean>
}