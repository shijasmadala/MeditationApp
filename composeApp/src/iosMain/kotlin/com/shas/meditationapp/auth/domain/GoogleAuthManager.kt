package com.shas.meditationapp.auth.domain

import com.shas.meditationapp.auth.data.GoogleUser
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//import cocoapods.GoogleSignIn.GIDSignIn

actual class GoogleAuthManager {

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun signIn(): GoogleUser? =
        suspendCoroutine { continutation ->


            val rootViewController =
                UIApplication.sharedApplication.keyWindow?.rootViewController


            if (rootViewController == null) {
                continutation.resume(null)
            } else {
                GIDSignIn.sharedInstance
                    .signInWithPresentingViewController(rootViewController) { gidSignInResult, nsError ->
                        nsError?.let { println("Error While signing: $nsError") }
                        val idToken = gidSignInResult?.user?.idToken?.tokenString
                        val profile = gidSignInResult?.user?.profile
                        if (idToken != null) {
                            val googleUser =
                                GoogleUser(
                                    id = idToken.toString(),
                                    name = profile?.name ?: "",
                                    profileUrl = profile?.imageURLWithDimension(320u)?.absoluteString,
                                    email = ""
                                )
                            continutation.resume(googleUser)
                        } else {
                            continutation.resume(null)
                        }
                    }
            }
        }

    actual fun signOut() {
//        GIDSignIn.sharedInstance.signOut()
    }
}