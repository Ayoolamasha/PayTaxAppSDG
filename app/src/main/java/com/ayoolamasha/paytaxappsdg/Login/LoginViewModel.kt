package com.ayoolamasha.paytaxappsdg.Login

import android.app.Application
import androidx.lifecycle.*
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiStatusResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpRequest
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpResponse
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRepository
import com.ayoolamasha.paytaxappsdg.Utils.NetworkHelper
import com.ayoolamasha.paytaxappsdg.Utils.UserSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository,
                                         private val networkHelper: NetworkHelper,
                                         private val userDataRepository: UserDataRepository, private val userSharedPreference: UserSharedPreference ) : ViewModel(){

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<UserDataPojo>> = userDataRepository.allUserData.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertUserDataViewModel(userDataPojo: UserDataPojo) = viewModelScope.launch {
        userDataRepository.saveUserDataHelper(userDataPojo)
        userSharedPreference.setIsLoggedIn(true)
    }

    fun clearBeforeInsert(userDataPojo: UserDataPojo) = viewModelScope.launch {
        userDataRepository.clearBeforeInsert(userDataPojo)
        userSharedPreference.setIsLoggedIn(true)
    }

    private val _mutableLogin = MutableLiveData<ApiStatusResult<Any>>()
    val mLoginRequestResponse : LiveData<ApiStatusResult<Any>> get()  =  _mutableLogin


    fun loginUsersVM(userTaxID: String, userPassword: String){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                val loginRequest = LoginRequest(userTaxID, userPassword)
                loginRepository.loginUser(loginRequest).let {
                    if (it.isSuccessful){
                        if (it.body() != null && it.code() == 200){
                            val success = it.body()
                            _mutableLogin.postValue(ApiStatusResult.successResult(success as LoginRequestResponse))
                        }else if (it.code() != 201){
                            _mutableLogin.postValue(ApiStatusResult.errorResult(it.errorBody().toString(), null))

                        }
                    }else _mutableLogin.postValue(ApiStatusResult.failureResult(it.message().toString(), null))
                }

            }else _mutableLogin.postValue(ApiStatusResult.networkError(true, "No internet connection"))
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}