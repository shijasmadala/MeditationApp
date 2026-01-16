package com.shas.meditationapp

import com.shas.meditationapp.song_details.domain.AudioPlayer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.seekToTime
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSURL

class IosAudioPlayer : AudioPlayer {
    private var player: AVPlayer? = null

    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long> = _duration

    private val _isBuffering = MutableStateFlow(false)
    override val isBuffering: StateFlow<Boolean> = _isBuffering

    @OptIn(ExperimentalForeignApi::class)
    override fun play(url: String) {
        val nslUrl = NSURL.URLWithString(url) ?: return
        player = AVPlayer(nslUrl)
        player?.play()
        _isPlaying.value = true
    }

    override fun pause() {
        player?.pause()
        _isPlaying.value = false
    }

    override fun stop() {
        player?.pause()
        player = null
        _isPlaying.value = false
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun seekTo(positionMs: Long) {
        val time = CMTimeMake(positionMs, 1000)
        player?.seekToTime(time)
    }

    override fun release() {
        player = null
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}