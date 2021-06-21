package com.ayoolamasha.paytaxappsdg.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRepository
import com.ayoolamasha.paytaxappsdg.Utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val allUserDataPojo: Flow<List<UserDataPojo>> = userDataRepository.allUserData

      fun getUserTaxPayerId(accessToken: String) = viewModelScope.launch{
         userDataRepository.getTaxPayerId(accessToken)

    }

    fun getAllLiveUserPojo(): Flow<List<UserDataPojo>> {
        return allUserDataPojo
    }




    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}