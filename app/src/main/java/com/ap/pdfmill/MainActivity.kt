package com.ap.pdfmill

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.ap.pdfmill.databinding.ActivityMainBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.IOUtils
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var binding: ActivityMainBinding
  lateinit var mDrive: Drive
  private val viewModel: MainViewModel by viewModels()

  // launcher for the AuthInit activity; waits for result
  // See: https://developer.android.com/training/basics/intents/result
  private val signInLauncher =
    registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
      viewModel.updateUser()
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navController = findNavController(R.id.nav_host_fragment_content_main)
    appBarConfiguration = AppBarConfiguration(navController.graph)
    setupActionBarWithNavController(navController, appBarConfiguration)

    PDFBoxResourceLoader.init(applicationContext);

    AuthInit(viewModel, signInLauncher)

    mDrive = getDriveService(this)!!
    val addAttachment = findViewById<Button>(R.id.uploadBut)
    addAttachment.setOnClickListener {
      GlobalScope.async(Dispatchers.IO) {
        uploadFileToGDrive(this@MainActivity)
      }
    }
  }

  private fun getDriveService(context: Context): Drive? {
    GoogleSignIn.getLastSignedInAccount(context).let { googleAccount ->
      val credential = GoogleAccountCredential.usingOAuth2(
        this, listOf(DriveScopes.DRIVE_FILE)
      )
      if (googleAccount != null) {
        credential.selectedAccount = googleAccount.account!!
      }
      return Drive.Builder(
        AndroidHttp.newCompatibleTransport(),
        JacksonFactory.getDefaultInstance(),
        credential
      )
        .setApplicationName(getString(R.string.app_name))
        .build()
    }
    return null
  }

  fun uploadFileToGDrive(context: Context) {
    mDrive.let { googleDriveService ->
      lifecycleScope.launch {
        try {
          val raunit = File("storage/emulated/0/Download", "test.pdf")
          val gfile = com.google.api.services.drive.model.File()
          gfile.name = "Subscribe"
          val mimetype = "image/png"
          val fileContent = FileContent(mimetype, raunit)
          var fileid = ""

          withContext(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
              launch {
                var mFile =
                  googleDriveService.Files().create(gfile, fileContent).execute()

              }
            }
          }
        } catch (userAuthEx: UserRecoverableAuthIOException) {
          startActivity(
            userAuthEx.intent
          )
        } catch (e: Exception) {
          e.printStackTrace()
          Log.d("asdf", e.toString())
          Toast.makeText(
            context,
            "Some Error Occured in Uploading Files" + e.toString(),
            Toast.LENGTH_LONG
          ).show()
        }
      }
    }
  }
}