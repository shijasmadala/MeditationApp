package com.shas.meditationapp.song_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shas.meditationapp.core.domain.onError
import com.shas.meditationapp.core.domain.onSuccess
import com.shas.meditationapp.song_details.domain.AudioPlayer
import com.shas.meditationapp.song_details.domain.repository.SongDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SongDetailViewModel(
    private val audioPlayer: AudioPlayer,
    private val songDetailsRepository: SongDetailsRepository
) : ViewModel() {

    private val _songDetailState = MutableStateFlow(SongDetailState())
    val songDetailState = _songDetailState

    private val _miniPlayerDismissed = MutableStateFlow(false)

    val isPlaying: StateFlow<Boolean> = audioPlayer.isPlaying
    val position: StateFlow<Long> = audioPlayer.currentPosition
    val duration: StateFlow<Long> = audioPlayer.duration
    val isBuffering = audioPlayer.isBuffering

    val showMiniPlayer: StateFlow<Boolean> = combine(
        _miniPlayerDismissed,
        _songDetailState,
        audioPlayer.isPlaying,
        audioPlayer.currentPosition,
        audioPlayer.duration
    ) { dismissed, state, playing, pos, dur ->
        val hasTrack = state.songDetail?.results?.firstOrNull() != null
        val hasProgress = dur > 0L && (playing || pos > 0L)
        !dismissed && hasTrack && hasProgress
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun play(url: String?) {
        _miniPlayerDismissed.value = false
        audioPlayer.play(url ?: "")
    }

    fun pause() = audioPlayer.pause()
    fun seekTo(ms: Long) = audioPlayer.seekTo(ms)

    override fun onCleared() {
        // AudioPlayer is a process-wide singleton; do not release here so playback
        // survives navigation when this ViewModel is scoped to MainNavGraph.
    }

    fun getTrackById(trackId: String?) = viewModelScope.launch {
        val prevId = _songDetailState.value.activeTrackId
        if (!trackId.isNullOrBlank() && prevId != null && prevId != trackId) {
            audioPlayer.reset()
            _songDetailState.value = SongDetailState(loading = true)
        } else {
            _songDetailState.update { it.copy(loading = true, error = null) }
        }
        if (prevId != trackId) {
            _miniPlayerDismissed.value = false
        }

        songDetailsRepository.getTrackById(trackId).onSuccess { resp ->
            _songDetailState.update {
                it.copy(
                    loading = false,
                    songDetail = resp,
                    activeTrackId = trackId
                )
            }
        }.onError { error ->
            _songDetailState.update {
                it.copy(loading = false, error = error.name)
            }
        }
    }

    fun dismissMiniPlayer() {
        audioPlayer.reset()
        _miniPlayerDismissed.value = true
        _songDetailState.value = SongDetailState()
    }

    val songTimer: StateFlow<Long> =
        combine(
            audioPlayer.duration,
            audioPlayer.currentPosition
        ) { dur, pos ->
            (dur - pos).coerceAtLeast(0L)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0L
        )

    val elapsedTime: StateFlow<Long> =
        audioPlayer.currentPosition
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0L
            )
}
