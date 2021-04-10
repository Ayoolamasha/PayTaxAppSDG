package com.ayoolamasha.paytaxappsdg.Utils

import android.content.Context
import android.content.SharedPreferences
import java.io.IOException
import java.security.GeneralSecurityException

class UserSharedPreference {

    private lateinit var context: Context
    private var instance: UserSharedPreference?=null

    companion object{
        val sharedPreference: SharedPreferences? = null
        val userSharedPreference: String = "user_shared_preference"
        var instance: UserSharedPreference? = null

        //val instance: UserSharedPreference?=null

        @Throws(GeneralSecurityException::class, IOException::class)
        fun getInstance(context: Context?): UserSharedPreference? {
            return instance ?: UserSharedPreference()
        }
    }
}