package com.shas.meditationapp.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shas.meditationapp.auth.domain.GoogleAuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val googleAuthManager: GoogleAuthManager
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState

    fun signIn() = viewModelScope.launch {
        _authState.update { it.copy(isLoading = true) }
        try {
            val user = googleAuthManager.signIn()
            _authState.update {
                it.copy(
                    isLoading = false,
                    userData = user,
                    error = if (user == null) "Login failed" else null
                )
            }
        } catch (e: Exception) {

            _authState.update {
                it.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun signOut() {
        googleAuthManager.signOut()
        _authState.value = AuthState()
    }
}