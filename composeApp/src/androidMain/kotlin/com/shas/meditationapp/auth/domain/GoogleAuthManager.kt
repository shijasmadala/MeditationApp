package com.shas.meditationapp.auth.domain

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.shas.meditationapp.auth.data.GoogleUser
import kotlinx.coroutines.tasks.await

actual class GoogleAuthManager(
    private val context: Context
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    actual suspend fun signIn(): GoogleUser? {
        return try {

            val googleIdOption =
                GetSignInWithGoogleOption.Builder(
                    "366676709058-2k8ulnvdm5spphj0vkph4tq55ubbraue.apps.googleusercontent.com"
                ).build()

            val request =
                GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

            val credentialManager =
                CredentialManager.create(context)

            val result =
                credentialManager.getCredential(
                    request = request,
                    context = context
                )

            val credential = result.credential

            val googleCredential =
                com.google.android.libraries.identity.googleid
                    .GoogleIdTokenCredential
                    .createFrom(credential.data)

            val firebaseCredential =
                GoogleAuthProvider.getCredential(
                    googleCredential.idToken,
                    null
                )

            val authResult =
                firebaseAuth
                    .signInWithCredential(firebaseCredential)
                    .await()

            val user = authResult.user ?: return null

            GoogleUser(
                id = user.uid,
                name = user.displayName,
                email = user.email,
                profileUrl = user.photoUrl?.toString()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun signOut() {
        firebaseAuth.signOut()
    }
}