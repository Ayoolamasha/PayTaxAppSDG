package com.ayoolamasha.paytaxappsdg.Utils

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ayoolamasha.paytaxappsdg.Login.LoginViewModel
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpViewModel

class ViewModelFactory(application: Application) : ViewModelProvider.Factory {


    @NonNull
    private lateinit var application: Application


    init {
        ViewModelFactory(application)
    }
    fun ViewModelFactory(@NonNull application: Application) {
        this.application = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == (SignUpViewModel::class.java)) {
            return SignUpViewModel(application) as T
        }else if (modelClass == (LoginViewModel::class.java)){
            return LoginViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}