package com.shas.meditationapp.song_details.presentation

import androidx.lifecycle.ViewModel
import com.shas.meditationapp.song_details.domain.AudioPlayer
import kotlinx.coroutines.flow.StateFlow

class SongDetailViewModel(private val audioPlayer: AudioPlayer) : ViewModel() {

    val isPlaying: StateFlow<Boolean> = audioPlayer.isPlaying
    val position: StateFlow<Long> = audioPlayer.currentPosition
    val duration: StateFlow<Long> = audioPlayer.duration

    fun play(url: String) = audioPlayer.play(url)
    fun pause() = audioPlayer.pause()
    fun seekTo(ms: Long) = audioPlayer.seekTo(ms)
    fun onClear() = audioPlayer.release()
}