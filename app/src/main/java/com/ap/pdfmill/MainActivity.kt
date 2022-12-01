package com.ap.pdfmill

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.ap.pdfmill.databinding.ActivityMainBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

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
                val file = applicationContext.getFileStreamPath("myfile")

                val gfile = com.google.api.services.drive.model.File()
                gfile.name = "myfile"
                val fileContent = FileContent("application/pdf", file)

                Log.d("file", "uploading")
                mDrive.Files().create(gfile, fileContent).execute()
                Log.d("file", "uploaded")
            }
        }
//            GlobalScope.async(Dispatchers.IO) {
//                uploadFileToGDrive(this@MainActivity)
//            }
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

//private fun uploadFileToGDrive(context: Context) {
//    mDrive.let { googleDriveService ->
//        lifecycleScope.launch {
//            try {
//                val file = context.getFileStreamPath("myfile")
//
////            File("myfile")
////          context.openFileInput("myfile").use {
////            file.writeBytes(it.readBytes())
////          }
//
//                val gfile = com.google.api.services.drive.model.File()
//                gfile.name = "myfile"
//                val fileContent = FileContent("application/pdf", file)
//                var fileid = ""
//
//                withContext(Dispatchers.Main) {
//                    withContext(Dispatchers.IO) {
//                        launch {
//                            Log.d("file", "uploading")
//                            googleDriveService.Files().create(gfile, fileContent).execute()
//                            Log.d("file", "uploaded")
//                        }
//                    }
//                }
//            } catch (userAuthEx: UserRecoverableAuthIOException) {
//                startActivity(
//                    userAuthEx.intent
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.d("asdf", e.toString())
//                Toast.makeText(
//                    context,
//                    "Some Error Occured in Uploading Files" + e.toString(),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }
//}
}