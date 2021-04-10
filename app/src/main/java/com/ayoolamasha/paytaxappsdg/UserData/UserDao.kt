package com.ayoolamasha.paytaxappsdg.UserData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

//    @Query("SELECT * FROM user_data")
//    fun getAllUserData(): Flow<List<UserDataPojo>>

    @Query("SELECT * FROM user_data")
    fun getAllUserData(): LiveData<List<UserDataPojo>>


//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun saveUserData(userDataPojo: UserDataPojo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveUserData(userDataPojo: UserDataPojo)


//    private val quoteList = mutableListOf<UserDataPojo>()
//    private val quotes = MutableLiveData<List<UserDataPojo>>()
//
//    init {
//        quotes.value = quoteList
//    }
//
//    fun addQuote(quote: Quote) {
//        quoteList.add(quote)
//        quotes.value = quoteList
//    }
//
//    fun getQuotes() = quotes as LiveData<List<Quote>>

}