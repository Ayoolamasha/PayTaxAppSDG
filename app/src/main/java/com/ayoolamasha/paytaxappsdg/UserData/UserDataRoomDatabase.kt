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

@Database(entities = [UserDataPojo::class], version = 1)
abstract class UserDataRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var instance: UserDataRoomDatabase? = null

        private const val DATABASE_NAME:String = "user_data"

//        fun getDatabase(context: Context, scope: CoroutineScope): UserDataRoomDatabase{
//            return INSTANCE ?: synchronized(this){
//                val instance = Room.databaseBuilder(context.applicationContext,
//                    UserDataRoomDatabase::class.java, DATABASE_NAME)
//                    .fallbackToDestructiveMigration()
//                    .addCallback(UserDatabaseCallBack(scope))
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//
//        private class UserDatabaseCallBack(private val scope: CoroutineScope):RoomDatabase.Callback(){
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                INSTANCE?. let {database  -> scope.launch(Dispatchers.IO){
//                    populateDatabase(database.userDao())
//
//                }   }
//            }
//
//        }
//
//        suspend fun populateDatabase(userDao: UserDao){
//            val userData = UserDataPojo("2","Jane", "Doe",
//                "janedoe@mail.com", "0800523456", "1234567#", "taxId123",
//                "ID")
//            userDao.saveUserData(userData)
//        }




        //private const val DATABASE_NAME:String = "user_data"
        private const val NUMBER_OF_THREAD:Int = 1
        val databaseWriterExecutor:ExecutorService = Executors.newFixedThreadPool(
            NUMBER_OF_THREAD)

        @Synchronized
        fun getInstance(context: Context): UserDataRoomDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataRoomDatabase::class.java, DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }
            return instance
        }

        private val callback: Callback = object : Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }
        }
    }


}