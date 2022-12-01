package com.ap.pdfmill

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.ap.pdfmill.databinding.ActivityMainBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.api.services.drive.Drive
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader

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
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_host_fragment_content_main, R.id.nav_da4856))
        setupActionBarWithNavController(navController, appBarConfiguration)

        PDFBoxResourceLoader.init(applicationContext);

        AuthInit(viewModel, signInLauncher)
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