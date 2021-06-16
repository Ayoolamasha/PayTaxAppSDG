package com.ayoolamasha.paytaxappsdg.UserData


import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface UserDaoHelper {
    suspend fun saveUserDataHelper(userDataPojo: UserDataPojo)
    suspend fun getAllUserData(): Flow<List<UserDataPojo>>
    fun getTaxPayerId(accessToken: String): UserDataPojo
    suspend fun clearBeforeInsert(userDataPojo: UserDataPojo)
}