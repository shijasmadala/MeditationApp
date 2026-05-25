package com.shas.meditationapp.auth.domain

import com.shas.meditationapp.auth.data.GoogleUser

expect class GoogleAuthManager {
    suspend fun signIn() : GoogleUser?
    fun signOut()
}