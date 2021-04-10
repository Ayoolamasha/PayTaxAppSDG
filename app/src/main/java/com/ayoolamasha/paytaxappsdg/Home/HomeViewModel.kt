package com.ayoolamasha.paytaxappsdg.Home

import android.app.Application
import androidx.lifecycle.*
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.Models.CalculateTaxRequest
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application){

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

    private lateinit var homeRepository: HomeRepository
    lateinit var mLiveGetTaxTypeResponse: LiveData<ApiResult<Any>>
    private lateinit var _mMutableGetTaxTypeResponse: MutableLiveData<ApiResult<Any>>

    init {
        homeRepository = HomeRepository.getInstance(application)
        _mMutableGetTaxTypeResponse = MutableLiveData()
        mLiveGetTaxTypeResponse = _mMutableGetTaxTypeResponse


    }

    fun getTaxTypeViewModel(){
        viewModelScope.launch {
            val getTypeResult = homeRepository.getTaxType()
            _mMutableGetTaxTypeResponse.postValue(getTypeResult)
        }
    }

    fun makeCalculationsViewModel(userTaxId:String, userIncome: String){
        viewModelScope.launch {
            val getPost = CalculateTaxRequest(userTaxId, userIncome)
            val getCalculations = homeRepository.makeCalculateRequest(getPost)
            _mMutableGetTaxTypeResponse.postValue(getCalculations)
        }
    }
}


