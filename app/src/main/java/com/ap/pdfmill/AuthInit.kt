package com.ap.pdfmill

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

// https://firebase.google.com/docs/auth/android/firebaseui
// https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#google-1
class AuthInit(viewModel: MainViewModel, signInLauncher: ActivityResultLauncher<Intent>) {
    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d(javaClass.name, "user null, launching sign-in intent")

            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            // Create and launch sign-in intent
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
                .also { signInLauncher.launch(it) }
        } else {
            Log.d(javaClass.name, "user logged in: ${user.displayName} | ${user.email}")
            viewModel.updateUser()
        }
    }
}
