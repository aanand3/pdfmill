package com.ap.pdfmill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {
    private var displayName = MutableLiveData("Uninitialized")
    private var email = MutableLiveData("Uninitialized")
    private var uid = MutableLiveData("Uninitialized")
    private var fileName = "";

    private fun userLogout() {
        displayName.postValue("No user")
        email.postValue("No email, no active user")
        uid.postValue("No uid, no active user")
    }

    fun updateUser() {
        // XXX Write me. Update user data in view model
        FirebaseAuth.getInstance().currentUser?.let {
            displayName.postValue(it.displayName)
            email.postValue(it.email)
            uid.postValue(it.uid)
        }
    }

    fun getFileName(): String {
        return fileName;
    }

    fun setFileName(newFileName: String){
        fileName = newFileName
    }

    fun observeDisplayName(): LiveData<String> {
        return displayName
    }

    fun observeEmail(): LiveData<String> {
        return email
    }

    fun observeUid(): LiveData<String> {
        return uid
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        userLogout()
    }
}