package com.shas.meditationapp.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shas.meditationapp.core.domain.onError
import com.shas.meditationapp.core.domain.onSuccess
import com.shas.meditationapp.core.presentation.toUiText
import com.shas.meditationapp.home.domain.repository.HomeRepository
import com.shas.meditationapp.util.Constants.CLIENT_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private var _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState
    init {
        getAllTracks("relaxing,ambient")
    }

     fun getAllTracks(tags: String) = viewModelScope.launch {
        _homeState.update { it.copy(loading = true) }
        homeRepository.getAllTracks(
            clientId = CLIENT_ID,
            formate = "json",
            order = "popularity_week_desc",
            tags = tags
        ).onSuccess { resp ->
            _homeState.update { it.copy(loading = false, homeTrack = resp, error = "") }
        }.onError { error ->
            _homeState.update { it.copy(loading = false, error = error.toUiText()) }
        }
    }
}