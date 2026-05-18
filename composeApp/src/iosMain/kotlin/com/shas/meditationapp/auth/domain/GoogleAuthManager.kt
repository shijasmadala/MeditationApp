package com.shas.meditationapp.auth.domain

import com.shas.meditationapp.auth.data.GoogleUser
import googleSignInBridge.GoogleSignInBridge_signIn
import googleSignInBridge.GoogleSignInBridge_signOut
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import platform.UIKit.UIApplication
import platform.objc.objcPtr
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class GoogleAuthManager {

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun signIn(): GoogleUser? = suspendCoroutine { continuation ->
        val rootViewController =
            UIApplication.sharedApplication.keyWindow?.rootViewController

        if (rootViewController == null) {
            continuation.resume(null)
            return@suspendCoroutine
        }

        val continuationRef = StableRef.create(continuation)

        GoogleSignInBridge_signIn(
            rootViewController.objcPtr(),
            staticCFunction { idToken, name, email, profileUrl ->
                val cont = continuationRef.get()
                val user = idToken?.toKString()?.let { token ->
                    GoogleUser(
                        id = token,
                        name = name?.toKString(),
                        email = email?.toKString(),
                        profileUrl = profileUrl?.toKString(),
                    )
                }
                continuationRef.dispose()
                cont.resume(user)
            },
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun signOut() {
        GoogleSignInBridge_signOut()
    }
}
