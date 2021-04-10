package com.ayoolamasha.paytaxappsdg.UserData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataPojo(
//    @PrimaryKey(autoGenerate = false)
//    val autoId: Int?,
    val accessToken:String,
    val _id:String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val password: String,
    val taxPayerId: String
)
