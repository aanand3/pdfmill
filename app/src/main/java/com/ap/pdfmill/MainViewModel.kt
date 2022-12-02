package com.ap.pdfmill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ap.pdfmill.da4856.Da4856
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {
    private var fileName = MutableLiveData<String>()
    private var da4856 = MutableLiveData<Da4856>()

    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun observeFileName(): LiveData<String> = fileName
    fun setFileName(newFileName: String){
        fileName.postValue(newFileName)
    }

    fun observeDa4856(): LiveData<Da4856> = da4856
    fun setDa4856(newDa4856: Da4856) {
        da4856.postValue(newDa4856)
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        userLogout()
    }
}