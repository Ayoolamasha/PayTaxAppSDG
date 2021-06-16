package com.ayoolamasha.paytaxappsdg.SignUp

import android.app.Application
import androidx.lifecycle.*
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiStatusResult
import com.ayoolamasha.paytaxappsdg.Utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpRepository: SignUpRepository,
                                              private val networkHelper: NetworkHelper
): ViewModel() {

//
//    private val _mutableSignUp = MutableLiveData<Event<ApiStatusResult<Any>>>()
//
//    val mLiveSignUpResponse: LiveData<Event<ApiStatusResult<Any>>> get() = _mutableSignUp

    private val _mutableSignUp = MutableLiveData<ApiStatusResult<Any>>()

    val mLiveSignUpResponse: LiveData<ApiStatusResult<Any>> get() = _mutableSignUp


    fun signUpUsersVM(userFirstName: String, userLastName: String, userEmail: String,
                            userPhone: String, userPassword: String){
        viewModelScope.launch {
            //_mutableSignUp.postValue(ApiStatusResult.loading(null))
            if (networkHelper.isNetworkConnected()){
                val signUpRequest = SignUpRequest(userFirstName, userLastName, userEmail, userPhone, userPassword)
                signUpRepository.signUpUser(signUpRequest).let {
                    if (it.isSuccessful){
                        if (it.body() != null && it.code() == 200){
                            val success = it.body()
                            _mutableSignUp.postValue(ApiStatusResult.successResult(success as SignUpResponse))
                        }else if (it.code() != 201){
                            _mutableSignUp.postValue(ApiStatusResult.errorResult(it.errorBody().toString(), null))

                        }
                    }else _mutableSignUp.postValue(ApiStatusResult.failureResult(it.message().toString(), null))
                }

            }else _mutableSignUp.postValue(ApiStatusResult.networkError(true, "No internet connection"))
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

