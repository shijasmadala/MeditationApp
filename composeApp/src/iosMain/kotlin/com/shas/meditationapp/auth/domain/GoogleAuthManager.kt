package com.shas.meditationapp.auth.domain

import cocoapods.GoogleSignIn.GIDConfiguration
import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.FirebaseAuth.FIRAuth
import cocoapods.FirebaseAuth.FIRGoogleAuthProvider
import com.shas.meditationapp.auth.data.GoogleUser
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
actual class GoogleAuthManager {
    private val clientId = "366676709058-hboq28etab2kr1a5kcv8v8s820th3thl.apps.googleusercontent.com"

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

            // Get Google tokens
            val idToken = user.idToken?.tokenString
            val accessToken = user.accessToken.tokenString

            if (idToken == null) {
                continuation.resume(null)
                return@signInWithPresentingViewController
            }

            // Create Firebase credential
            val credential = FIRGoogleAuthProvider.credentialWithIDToken(
                idToken = idToken,
                accessToken = accessToken
            )

            // Sign in to Firebase with Google credential
            FIRAuth.auth().signInWithCredential(credential) { authResult, firebaseError ->
                if (firebaseError != null) {
                    continuation.resume(null)
                    return@signInWithCredential
                }

                // Now user is saved in Firebase Auth
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
    }

    actual fun signOut() {
        GIDSignIn.sharedInstance.signOut()
        FIRAuth.auth().signOut(null) // ✅ Also sign out from Firebase
    }
}