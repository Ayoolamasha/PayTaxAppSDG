package com.ayoolamasha.paytaxappsdg.Login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

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


    private lateinit var loginRepository: LoginRepository

    var mLiveLoginResponse: LiveData<ApiResult<Any>>
    private var _mMutableLoginResponse: MutableLiveData<ApiResult<Any>>

    init {
        loginRepository = LoginRepository.getInstance(application)
        _mMutableLoginResponse = MutableLiveData()
        mLiveLoginResponse = _mMutableLoginResponse
    }

    fun userLogin(userTaxId: String, userPassword:String){
        viewModelScope.launch {
            val loginRequest = LoginRequest(userTaxId, userPassword)
            val loginResult = loginRepository.makeLoginRequest(loginRequest)
            _mMutableLoginResponse.postValue(loginResult)
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