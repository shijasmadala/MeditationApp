package com.shas.meditationapp

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.shas.meditationapp.song_details.domain.AudioPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AndroidAudioPlayer(
    private val context: Context
) : AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    /** Coroutine setup */
    private val playerScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )
    private var progressJob: Job? = null

    /** State */
    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long> = _duration
    private var currentUrl: String? = null
    override fun play(url: String) {
        // ▶️ Resume case
        if (mediaPlayer != null && currentUrl == url) {
            mediaPlayer?.start()
            _isPlaying.value = true
            startProgressUpdates()
            return
        }

        // ▶️ New song case
        releaseInternal()
        currentUrl = url

        mediaPlayer = MediaPlayer().apply {

            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            setDataSource(url)

            setOnPreparedListener { mp ->
                _duration.value = mp.duration.toLong()
                mp.start()
                _isPlaying.value = true
                startProgressUpdates()
            }

            setOnCompletionListener {
                _isPlaying.value = false
                stopProgressUpdates()
            }

            setOnErrorListener { _, _, _ ->
                _isPlaying.value = false
                stopProgressUpdates()
                true
            }

            prepareAsync() // 🔥 NON-BLOCKING
        }
    }

    override fun pause() {
        mediaPlayer?.pause()
        _isPlaying.value = false
        stopProgressUpdates()
    }

    override fun stop() {
        mediaPlayer?.stop()
        _isPlaying.value = false
        stopProgressUpdates()
    }

    override fun seekTo(positionMs: Long) {
        mediaPlayer?.seekTo(positionMs.toInt())
    }

    override fun release() {
        releaseInternal()
        playerScope.cancel()
    }

    override fun reset() {
        //reset alla state
        stopProgressUpdates()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        currentUrl = null

        _isPlaying.value = false
        _currentPosition.value = 0L
        _duration.value = 0L
    }

    // -------------------------
    // Coroutine helpers
    // -------------------------

    private fun startProgressUpdates() {
        stopProgressUpdates()
        progressJob = playerScope.launch {
            while (isActive && mediaPlayer != null) {
                _currentPosition.value =
                    mediaPlayer?.currentPosition?.toLong() ?: 0L
                delay(1000)
            }
        }
    }

    private fun stopProgressUpdates() {
        progressJob?.cancel()
        progressJob = null
    }

    private fun releaseInternal() {
        stopProgressUpdates()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
