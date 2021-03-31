package com.ayoolamasha.paytaxappsdg.SignUp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class SignUpViewModel(application: Application) : AndroidViewModel(application){

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    private lateinit var signUpRepository: SignUpRepository

    var mLiveSignUpResponse: LiveData<ApiResult<Any>>

    private val mMutableSignUpResponse: MutableLiveData<ApiResult<Any>>

    init {
        signUpRepository = SignUpRepository.getInstance(application)
        mMutableSignUpResponse = MutableLiveData()
        mLiveSignUpResponse = mMutableSignUpResponse
    }

    fun userSignUp(userFirstName: String, userLastName: String, userEmail: String,
    userPhone: String, userPassword: String){
        viewModelScope.launch {
            val signUpRequest = SignUpRequest(userFirstName, userLastName, userEmail, userPhone, userPassword)
            val signUpResult = signUpRepository.makeSignUpRequest(signUpRequest)
            mMutableSignUpResponse.postValue(signUpResult)

        }
    }


    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }




}