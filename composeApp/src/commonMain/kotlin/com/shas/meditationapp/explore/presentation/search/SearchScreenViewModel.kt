package com.shas.meditationapp.explore.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shas.meditationapp.core.domain.onError
import com.shas.meditationapp.core.domain.onSuccess
import com.shas.meditationapp.explore.domain.repository.ExploreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel(private val exploreRepository: ExploreRepository) : ViewModel() {
    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state

    init {
        getSearchData("meditation", "")
    }

    private fun getSearchData(name: String, artist: String) = viewModelScope.launch {
        _state.value = _state.value.copy(loading = true)
        exploreRepository.searchTrackByNameAndArtist(nameSearch = name, artistName = artist)
            .onSuccess { resp ->
                _state.update { it.copy(loading = false, searchedTrack = resp) }
            }.onError { resp ->
                _state.update {
                    it
                        .copy(loading = false, error = it.error)
                }
            }
    }
}