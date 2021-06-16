package com.ayoolamasha.paytaxappsdg.Utils


import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

private const val LOGGED_IN = "LOGGED_IN"
private const val AUTH_TOKEN = "AUTH_TOKEN"
private const val USER_NAME = "USER_NAME"

class UserSharedPreference @Inject constructor(private val preferences: SharedPreferences) {

    fun setIsLoggedIn(enabled:Boolean){
        preferences.edit{putBoolean(LOGGED_IN, enabled)}
    }

    fun setAuthToken(authToken:String){
        preferences.edit{putString(AUTH_TOKEN, authToken)}
    }

    fun setLoggedInUser(userName:String){
        preferences.edit{putString(USER_NAME, userName)}
    }

    fun isUserLoggedIn() = preferences.getBoolean(LOGGED_IN, false)
    fun getAuthToken() = preferences.getString(AUTH_TOKEN, null)
    fun getLoggedInUser() = preferences.getString(USER_NAME, null)
    fun clear()= preferences.edit().clear().apply()

}