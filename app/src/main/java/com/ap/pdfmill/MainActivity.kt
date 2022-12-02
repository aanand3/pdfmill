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

    // launcher for the AuthInit activity; waits for result
    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up nav bar
        // todo: there's a back arrow on the top level for some reason, and none of the other back arrows work - this is weird bc the actual system's up arrow works
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(emptySet())
        setupActionBarWithNavController(navController, appBarConfiguration)

        // load the context into PDFBox
        PDFBoxResourceLoader.init(applicationContext);

        // start the login flow
        AuthInit(signInLauncher)
    }
}