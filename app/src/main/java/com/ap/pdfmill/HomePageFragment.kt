package com.ap.pdfmill

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.api.services.drive.model.File
import kotlinx.coroutines.*

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

        binding.logoutBut.setOnClickListener {
            // XXX Write me.
            viewModel.signOut()
        }
        binding.loginBut.setOnClickListener {
            // XXX Write me.
            AuthInit(viewModel, signInLauncher)
        }

        binding.da4856Button.setOnClickListener {
            findNavController().navigate(R.id.nav_da4856)
//            parentFragmentManager.apply {
//                beginTransaction()
//                    .add(Da4856Fragment.newInstance(), "da4856")
//                    .addToBackStack("da4856")
//                    .commit()
//            }
        }

        mDrive = getDriveService(requireContext())!!

        binding.uploadBut.setOnClickListener {
            Log.d("file", "upload button pressed")
            MainScope().launch {
                val defer = async(Dispatchers.IO) {
                    uploadToDrive()
                }
                defer.await()
                Toast.makeText(
                    context,
                    "Uploaded to Drive!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun uploadToDrive() {
        val file = context?.getFileStreamPath("myfile")

        val gfile = File()
        gfile.name = "myfile"
        val fileContent = FileContent("application/pdf", file)

        Log.d("file", "uploading")
        mDrive.Files().create(gfile, fileContent).execute()
    }

    private fun getDriveService(context: Context): Drive? {
        GoogleSignIn.getLastSignedInAccount(context).let { googleAccount ->
            val credential = GoogleAccountCredential.usingOAuth2(
                context, listOf(DriveScopes.DRIVE_FILE)
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