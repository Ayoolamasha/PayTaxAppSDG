package com.ayoolamasha.paytaxappsdg.UserData

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData


class UserDataRepository {

    private lateinit var userDao: UserDao
    private lateinit var context: Context

    //val allUserData: Flow<List<UserDataPojo>> = userDao.getAllUserData()
    lateinit var allUserData: LiveData<List<UserDataPojo>>


//
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun saveUserDataRepository(userDataPojo: UserDataPojo){
//        userDao.saveUserData(userDataPojo)
//    }

    fun UserDataRepository(application:Application){
        val userDataRoomDatabase = UserDataRoomDatabase.getInstance(application)
        if (userDataRoomDatabase != null) {
            userDao = userDataRoomDatabase.userDao()
            allUserData = userDao.getAllUserData()


    }    }






    fun saveUserDataRepository(userDataPojo: UserDataPojo){
        UserDataRoomDatabase.databaseWriterExecutor.execute {
            userDao.saveUserData(userDataPojo)

        }

 }








}