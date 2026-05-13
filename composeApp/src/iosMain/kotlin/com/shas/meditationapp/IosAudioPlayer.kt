package com.shas.meditationapp

import com.shas.meditationapp.song_details.domain.AudioPlayer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.asset
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.seekToTime
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMake
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSURL

class IosAudioPlayer : AudioPlayer {

    private var player: AVPlayer? = null
    private var currentUrl: String? = null

    private val playerScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    private var progressJob: Job? = null

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

        // Resume same song
        if (player != null && currentUrl == url) {
            player?.play()
            _isPlaying.value = true
            startProgressUpdates()
            return
        }

        reset()

        currentUrl = url

        val nsUrl = NSURL.URLWithString(url) ?: return

        val item = AVPlayerItem(nsUrl)

        player = AVPlayer(playerItem = item)

        player?.play()

        _isPlaying.value = true
        _isBuffering.value = false

        // Duration
        val durationSeconds =
            CMTimeGetSeconds(item.asset.duration)

        if (!durationSeconds.isNaN()) {
            _duration.value =
                (durationSeconds * 1000).toLong()
        }

        startProgressUpdates()
    }

    override fun pause() {
        player?.pause()

        _isPlaying.value = false

        stopProgressUpdates()
    }

    override fun stop() {
        player?.pause()

        stopProgressUpdates()

        _isPlaying.value = false
        _currentPosition.value = 0L
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun seekTo(positionMs: Long) {

        val time = CMTimeMakeWithSeconds(
            positionMs.toDouble() / 1000.0,
            preferredTimescale = 1000
        )

        player?.seekToTime(time)

        _currentPosition.value = positionMs
    }

    override fun release() {
        stopProgressUpdates()

        player?.pause()
        player = null

        _isPlaying.value = false
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun reset() {

        stopProgressUpdates()

        player?.pause()

        player?.seekToTime(
            CMTimeMake(0, 1)
        )

        player = null
        currentUrl = null

        _isPlaying.value = false
        _currentPosition.value = 0L
        _duration.value = 0L
        _isBuffering.value = false
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun startProgressUpdates() {

        stopProgressUpdates()

        progressJob = playerScope.launch {

            while (isActive && player != null) {

                val currentSeconds =
                    CMTimeGetSeconds(
                        player?.currentTime()
                            ?: CMTimeMake(0, 1)
                    )

                if (!currentSeconds.isNaN()) {

                    _currentPosition.value =
                        (currentSeconds * 1000).toLong()
                }

                delay(1000)
            }
        }
    }

    private fun stopProgressUpdates() {
        progressJob?.cancel()
        progressJob = null
    }
}