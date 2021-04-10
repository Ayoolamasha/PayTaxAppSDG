package com.ayoolamasha.paytaxappsdg

import android.app.Application
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRepository
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication : Application() {
//    val applicationScope = CoroutineScope(SupervisorJob())
//
//    val database by lazy { UserDataRoomDatabase.getDatabase(this, applicationScope) }
//    val repository by lazy { UserDataRepository(database.userDao()) }
}