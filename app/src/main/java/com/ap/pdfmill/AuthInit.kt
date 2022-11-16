package edu.utap.firebaseauth

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.ap.pdfmill.MainViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

// https://firebase.google.com/docs/auth/android/firebaseui
class AuthInit(viewModel: MainViewModel, signInLauncher: ActivityResultLauncher<Intent>) {
    companion object {
        private const val TAG = "AuthInit"
        fun setDisplayName(displayName: String, viewModel: MainViewModel) {
            FirebaseAuth.getInstance().currentUser?.let {
                it.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
                ).addOnCompleteListener {
                    viewModel.updateUser()
                }
            }
        }
    }

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d(TAG, "XXX user null")
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            // Create and launch sign-in intent
            // XXX Write me. Set authentication providers and start sign-in for user
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
                .also { signInLauncher.launch(it) }
        } else {
            Log.d(TAG, "XXX user ${user.displayName} email ${user.email}")
            viewModel.updateUser()
        }
    }
}
