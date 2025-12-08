package com.shas.meditationapp.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shas.meditationapp.core.domain.onError
import com.shas.meditationapp.core.domain.onSuccess
import com.shas.meditationapp.home.domain.repository.HomeRepository
import com.shas.meditationapp.util.Constants.CLIENT_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExploreViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private val _state = MutableStateFlow(ExploreUiState())
    val state = _state

    fun getTrendingTrack() = viewModelScope.launch {
        _state.update {
            it
                .copy(loading = true)
        }
        homeRepository.getTrendingTrack(
            clientId = CLIENT_ID,
            formate = "json",
            order = "popularity_total"
        ).onSuccess { resp ->
            _state.update {
                it
                    .copy(loading = false, error = null, trendingTrack = resp)
            }
        }.onError { error ->
            _state.update {
                it
                    .copy(loading = false, error = error.name)
            }
        }
    }
}