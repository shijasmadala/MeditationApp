package com.shas.meditationapp.auth.presentation

import com.shas.meditationapp.auth.data.GoogleUser

data class AuthState(
    val userData: GoogleUser? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
