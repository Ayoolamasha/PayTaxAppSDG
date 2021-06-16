package com.ayoolamasha.paytaxappsdg.UserData

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [UserDataPojo::class], version = 1, exportSchema = false)
abstract class UserDataRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

