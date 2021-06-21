package com.ayoolamasha.paytaxappsdg.UserData

import android.app.Application
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepository @Inject constructor (private val userDao: UserDao): UserDaoHelper {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allUserData: Flow<List<UserDataPojo>> = userDao.getAllUserData()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
     override suspend fun saveUserDataHelper(userDataPojo: UserDataPojo) = userDao.saveUserData(userDataPojo)
    override suspend fun getAllUserData(): Flow<List<UserDataPojo>> = userDao.getAllUserData()
    override fun getTaxPayerId(accessToken:String): UserDataPojo = userDao.getTaxPayerId(accessToken)
    override suspend fun clearBeforeInsert(userDataPojo: UserDataPojo) = userDao.clearBeforeInsert(userDataPojo)
    //suspend fun insertUserRepository(userDataPojo: UserDataPojo) = userDaoHelper.saveUserDataHelper(userDataPojo)
    //suspend fun saveUserDataHelper(userDataPojo: UserDataPojo) = userDao.saveUserData(userDataPojo)



}
