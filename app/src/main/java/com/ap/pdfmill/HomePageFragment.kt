package com.ap.pdfmill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ap.pdfmill.databinding.HomePageBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import java.io.File

class HomePageFragment : Fragment() {
  private var _binding: HomePageBinding? = null
  private val viewModel: MainViewModel by viewModels()
  lateinit var mDrive: Drive

  // launcher for the AuthInit activity; waits for result
  // See: https://developer.android.com/training/basics/intents/result
  private val signInLauncher =
    registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
      viewModel.updateUser()
    }
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = HomePageBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val mainActivity = requireActivity() as MainActivity
    // XXX Write me
    if (savedInstanceState == null) {
      binding.displayNameET.text.clear()
    }
    // XXX Write me. Set data to display in UI
    viewModel.observeEmail().observe(mainActivity) {
      binding.userEmail.text = it
    }
    viewModel.observeUid().observe(mainActivity) {
      binding.userUid.text = it
    }
    viewModel.observeDisplayName().observe(mainActivity) {
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

    binding.da31Button.setOnClickListener{
      val bundle = bundleOf("index" to 0)
      findNavController().navigate(R.id.Da31Fragment, bundle)
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}