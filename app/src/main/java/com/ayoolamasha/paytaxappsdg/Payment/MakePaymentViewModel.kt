package com.ayoolamasha.paytaxappsdg.Payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.Models.PaymentRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MakePaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModel= SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModel + Dispatchers.Main)

    private lateinit var makePaymentRepository: MakePaymentRepository
    lateinit var mLiveMakePaymentResponse: LiveData<ApiResult<Any>>
    private lateinit var _mMutableMakePaymentResponse: MutableLiveData<ApiResult<Any>>

    init {
        makePaymentRepository = MakePaymentRepository.getInstance(application)
        _mMutableMakePaymentResponse = MutableLiveData()
        mLiveMakePaymentResponse = _mMutableMakePaymentResponse

    }

    fun makePaymentViewModel(amount:String, email:String, fullName:String, taxType:String){
        viewModelScope.launch {
            val paymentRequest = PaymentRequest(amount, email, fullName, taxType)
            val makePaymentRequest = makePaymentRepository.makeLoginRequest(paymentRequest)
            _mMutableMakePaymentResponse.postValue(makePaymentRequest)

        }
    }

}