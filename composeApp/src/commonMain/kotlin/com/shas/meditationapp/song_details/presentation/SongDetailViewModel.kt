package com.shas.meditationapp.song_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shas.meditationapp.core.domain.onError
import com.shas.meditationapp.core.domain.onSuccess
import com.shas.meditationapp.song_details.domain.AudioPlayer
import com.shas.meditationapp.song_details.domain.repository.SongDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SongDetailViewModel(
    private val audioPlayer: AudioPlayer,
    private val songDetailsRepository: SongDetailsRepository
) : ViewModel() {

    private var _songDetailState = MutableStateFlow(SongDetailState())
    val songDetailState = _songDetailState

    val isPlaying: StateFlow<Boolean> = audioPlayer.isPlaying
    val position: StateFlow<Long> = audioPlayer.currentPosition
    val duration: StateFlow<Long> = audioPlayer.duration

    fun play(url: String?) = audioPlayer.play(url ?: "")
    fun pause() = audioPlayer.pause()
    fun seekTo(ms: Long) = audioPlayer.seekTo(ms)
    override fun onCleared() {
        audioPlayer.release()
    }

    fun getTrackById(trackId: String?) = viewModelScope.launch {
        _songDetailState.update {
            it
                .copy(loading = true)
        }
        songDetailsRepository.getTrackById(trackId).onSuccess { resp ->
            _songDetailState.update {
                it
                    .copy(loading = false, songDetail = resp)
            }
        }.onError { error ->
            _songDetailState.update {
                it
                    .copy(loading = false, error = error.name)
            }
        }
    }

    fun onExitScreen() {
        audioPlayer.reset()
        _songDetailState.value = SongDetailState() // clear UI state
    }
}