package com.shas.meditationapp.explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shas.meditationapp.core.domain.onError
import com.shas.meditationapp.core.domain.onSuccess
import com.shas.meditationapp.explore.domain.repository.ExploreRepository
import com.shas.meditationapp.home.domain.repository.HomeRepository
import com.shas.meditationapp.util.Constants.CLIENT_ID
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val homeRepository: HomeRepository,
    private val exploreRepository: ExploreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ExploreUiState())
    val state = _state

    init {
        loadData()
    }


    fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }

        val trendingDeferred = async { getTrendingTrack() }
        val albumDeferred = async { getPopularAlbum() }
        val popularPlayList = async { getPopularPlayList() }

        trendingDeferred.await()
        albumDeferred.await()
        popularPlayList.await()

        _state.update { it.copy(loading = false) }
    }

    suspend fun getTrendingTrack() {
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

    suspend fun getPopularAlbum() {
        exploreRepository.getPopularAlbums(order = "popularity_week", limit = "20")
            .onSuccess { resp ->
                _state.update {
                    it
                        .copy(loading = false, error = null, popularAlbum = resp)
                }
            }.onError { error ->
                _state.update {
                    it
                        .copy(loading = false, error = error.name)
                }
            }
    }

    suspend fun getPopularPlayList() {
        exploreRepository.getPopularAlbums(order = "popularity_total", limit = "20")
            .onSuccess { resp ->
                _state.update {
                    it
                        .copy(loading = false, error = null, popularPlayList = resp)
                }
            }.onError { error ->
                _state.update {
                    it
                        .copy(loading = false, error = error.name)
                }
            }
    }
}