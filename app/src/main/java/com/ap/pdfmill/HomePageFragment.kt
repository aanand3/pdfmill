package com.ap.pdfmill

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ap.pdfmill.databinding.HomePageBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes.DRIVE_FILE
import com.google.api.services.drive.model.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomePageFragment : Fragment() {
  private var _binding: HomePageBinding? = null
  private val viewModel: MainViewModel by activityViewModels()
  lateinit var mDrive: Drive
  private var fileName = "invalid"

  // launcher for the AuthInit activity; waits for result
  // See: https://developer.android.com/training/basics/intents/result
  private val signInLauncher =
    registerForActivityResult(FirebaseAuthUIActivityResultContract()) {}

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

    binding.logoutBut.setOnClickListener {
      viewModel.signOut()
    }
    binding.loginBut.setOnClickListener {
      AuthInit(signInLauncher)
    }

    binding.da4856Button.setOnClickListener {
      findNavController().navigate(R.id.nav_da4856)
    }

    viewModel.observeFileName().observe(viewLifecycleOwner) {
      fileName = it
    }

    mDrive = getDriveService(requireContext())!!

    binding.uploadBut.setOnClickListener {
      Log.d("file", "upload button pressed")
      if (fileName == "invalid") {
        Toast.makeText(
          activity,
          "You need to fill in a file before uploading!",
          LENGTH_LONG
        ).show()
      } else {
        binding.idPBLoading.isVisible = true
        MainScope().launch {
          val defer = async(Dispatchers.IO) {
            uploadToDrive()
          }
          defer.await()
          Toast.makeText(
            context,
            "Uploaded to Drive!",
            LENGTH_LONG
          ).show()
        }
        binding.idPBLoading.isVisible = false
      }

    }
  }

  private fun uploadToDrive() {
    Log.d("filename", fileName)
    val file = context?.getFileStreamPath(fileName)

    val gfile = File()
    gfile.name = fileName
    val fileContent = FileContent("application/pdf", file)

    Log.d("file", "uploading")
    try {
      mDrive.Files().create(gfile, fileContent).execute()
    } catch (e: Exception) {
      Log.e("error: ", e.stackTraceToString())
      Toast.makeText(
        activity,
        "File failed to upload",
        LENGTH_LONG
      ).show()
    }
  }

  private fun getDriveService(context: Context): Drive? {
    GoogleSignIn.getLastSignedInAccount(context).let { googleAccount ->
      val credential = GoogleAccountCredential.usingOAuth2(
        context, listOf(DRIVE_FILE)
      )
      if (googleAccount != null) {
        credential.selectedAccount = googleAccount.account!!
      }
      return Drive.Builder(
        AndroidHttp.newCompatibleTransport(),
        JacksonFactory.getDefaultInstance(),
        credential
      )
        .setApplicationName(R.string.app_name.toString())
        .build()
    }
  }


  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}