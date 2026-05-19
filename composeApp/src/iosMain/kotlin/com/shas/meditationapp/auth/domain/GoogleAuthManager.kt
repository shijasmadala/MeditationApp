package com.shas.meditationapp.auth.domain

import com.shas.meditationapp.auth.data.GoogleUser
import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDConfiguration

actual class GoogleAuthManager {
    private val clientId = "YOUR_IOS_CLIENT_ID.apps.googleusercontent.com"

    actual suspend fun signIn(): GoogleUser? = suspendCoroutine { continuation ->

        val config = GIDConfiguration(clientID = clientId)
        GIDSignIn.sharedInstance.configuration = config

        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController

        if (rootViewController == null) {
            continuation.resume(null)
            return@suspendCoroutine
        }

        GIDSignIn.sharedInstance.signInWithPresentingViewController(rootViewController) { result, error ->
            if (error != null || result == null) {
                continuation.resume(null)
                return@signInWithPresentingViewController
            }

            val user = result.user
            val profile = user.profile

            continuation.resume(
                GoogleUser(
                    id = user.userID ?: "",
                    name = profile?.name,
                    email = profile?.email,
                    profileUrl = profile?.imageURLWithDimension(200u)?.absoluteString
                )
            )
        }
    }

    actual fun signOut() {
        GIDSignIn.sharedInstance.signOut()
    }
}