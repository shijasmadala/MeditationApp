package com.shas.meditationapp.auth.domain

import cocoapods.GoogleSignIn.GIDSignIn
import com.shas.meditationapp.auth.data.GoogleUser
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class GoogleAuthManager {

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun signIn(): GoogleUser? =
        suspendCoroutine { continuation ->
            val rootViewController =
                UIApplication.sharedApplication.keyWindow?.rootViewController

            if (rootViewController == null) {
                continuation.resume(null)
            } else {
                GIDSignIn.sharedInstance
                    .signInWithPresentingViewController(rootViewController) { gidSignInResult, nsError ->
                        nsError?.let { println("Error while signing in: $nsError") }
                        val idToken = gidSignInResult?.user?.idToken?.tokenString
                        val profile = gidSignInResult?.user?.profile
                        if (idToken != null) {
                            continuation.resume(
                                GoogleUser(
                                    id = idToken,
                                    name = profile?.name,
                                    email = profile?.email,
                                    profileUrl = profile?.imageURLWithDimension(320u)?.absoluteString,
                                )
                            )
                        } else {
                            continuation.resume(null)
                        }
                    }
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    actual fun signOut() {
        GIDSignIn.sharedInstance.signOut()
    }
}
