package com.shas.meditationapp

import android.content.Context
import android.media.MediaPlayer
import com.shas.meditationapp.song_details.domain.AudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AndroidAudioPlayer(context: Context) : AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long> = _duration

    override fun play(url: String) {
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepare()
            start()
            _duration.value = duration.toLong()
            _isPlaying.value = true
        }
    }

    override fun pause() {
        mediaPlayer?.pause()
        _isPlaying.value = false
    }

    override fun stop() {
        mediaPlayer?.stop()
        _isPlaying.value = false
    }

    override fun seekTo(positionMs: Long) {
        mediaPlayer?.seekTo(positionMs.toInt())
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

}