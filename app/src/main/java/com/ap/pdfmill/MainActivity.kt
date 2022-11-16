package com.ap.pdfmill

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ap.pdfmill.databinding.ActivityMainBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import edu.utap.firebaseauth.AuthInit

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            viewModel.updateUser()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            binding.displayNameET.text.clear()
        }
        // XXX Write me. Set data to display in UI
        viewModel.observeEmail().observe(this) {
            binding.userEmail.text = it
        }
        viewModel.observeUid().observe(this) {
            binding.userUid.text = it
        }
        viewModel.observeDisplayName().observe(this) {
            binding.userName.text = it
        }

        binding.logoutBut.setOnClickListener {
            // XXX Write me.
            viewModel.signOut()
        }
        binding.loginBut.setOnClickListener {
            // XXX Write me.
            AuthInit(viewModel, signInLauncher)
        }
        binding.setDisplayName.setOnClickListener {
            // XXX Write me.
            if (binding.displayNameET.text.toString().isNotBlank()) {
                AuthInit.setDisplayName(binding.displayNameET.text.toString(), viewModel)
            }
        }

        AuthInit(viewModel, signInLauncher)
    }
}