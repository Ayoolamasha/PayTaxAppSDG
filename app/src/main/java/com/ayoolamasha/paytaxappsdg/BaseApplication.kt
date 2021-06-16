package com.ayoolamasha.paytaxappsdg

import android.app.Application
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRepository
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRoomDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class BaseApplication : Application() {
    //val applicationScope = CoroutineScope(SupervisorJob())

//    val database by lazy { UserDataRoomDatabase.getDatabase(this) }
//    val repository by lazy { UserDataRepository(database.userDao()) }
}