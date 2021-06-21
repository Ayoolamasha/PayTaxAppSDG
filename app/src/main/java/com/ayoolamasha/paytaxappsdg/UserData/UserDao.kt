package com.ayoolamasha.paytaxappsdg.UserData

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {

    @Query("SELECT * FROM user_data")
    abstract fun getAllUserData(): Flow<List<UserDataPojo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun saveUserData(userDataPojo: UserDataPojo)

    @Query("DELETE FROM user_data")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM user_data WHERE accessToken = :accessToken")
    abstract fun getTaxPayerId(accessToken: String?): UserDataPojo


    @Transaction
    open suspend fun clearBeforeInsert(userDataPojo: UserDataPojo){
        deleteAll()
        saveUserData(userDataPojo)
    }
}