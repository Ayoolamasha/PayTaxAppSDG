package com.ayoolamasha.paytaxappsdg.Utils
//
//import android.app.Application
//import androidx.annotation.NonNull
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.ayoolamasha.paytaxappsdg.UserData.UserDataRepository
//
//class ViewModelFactory(application: Application) : ViewModelProvider.Factory {
//
//
//    @NonNull
//    private lateinit var application: Application
//    //private lateinit var repository: UserDataRepository
//
//
//    init {
//        ViewModelFactory(application)
//        //ViewModelFactory(application, repository)
//    }
//    fun ViewModelFactory(@NonNull application: Application) {
//        this.application = application
//    }
//
////    fun ViewModelFactory(@NonNull application: Application, @NonNull userDataRepository: UserDataRepository) {
////        this.application = application
////        this.repository = userDataRepository
////    }
//
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
////
////    if (modelClass == (SignUpViewModel::class.java)) {
////        return SignUpViewModel(application) as T
////    }
//
////    if (modelClass == (LoginViewModel::class.java)){
////        return LoginViewModel(application) as T
////    }else
//
//
//}