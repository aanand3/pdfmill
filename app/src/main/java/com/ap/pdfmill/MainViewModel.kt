package com.ap.pdfmill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {
    private var fileName = MutableLiveData<String>()


    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun getFileName(): String {
        return fileName.value ?: "invalid";
    }

    fun observeFileName(): LiveData<String> = fileName

    fun setFileName(newFileName: String){
        fileName.postValue(newFileName)
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        userLogout()
    }
}