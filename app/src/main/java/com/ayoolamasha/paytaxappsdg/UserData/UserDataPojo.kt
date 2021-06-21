package com.ayoolamasha.paytaxappsdg.UserData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import dagger.Provides
import javax.inject.Singleton

@Entity(tableName = "user_data")

data class UserDataPojo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name= "id")
    val autoId: Int?,
    @ColumnInfo(name="accessToken")
    val accessToken:String,
    @ColumnInfo(name = "_id")
    val _id:String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "taxPayerId")
    val taxPayerId: String
)
