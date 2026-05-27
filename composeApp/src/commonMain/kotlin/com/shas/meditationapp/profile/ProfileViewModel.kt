package com.shas.meditationapp.profile

import SessionManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state

    init {
        getSavedUser()
    }

    //get saved user from preference
    fun getSavedUser() {
        val user = sessionManager.getUser()
        _state.update { it.copy(savedUser = user) }
    }
}