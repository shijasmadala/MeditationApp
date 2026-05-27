package com.shas.meditationapp.profile

import com.shas.meditationapp.auth.data.GoogleUser

data class ProfileUiState(
    val savedUser: GoogleUser? = null
)