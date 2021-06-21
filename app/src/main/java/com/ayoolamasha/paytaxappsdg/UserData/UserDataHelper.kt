package com.ayoolamasha.paytaxappsdg.UserData

interface UserDataHelper {
    suspend fun getAccessToken(accessToken:String): UserDataPojo
    fun getFirstName(): UserDataPojo
    suspend fun getLastName(lastName: String): UserDataPojo
    suspend fun getTaxPayerId(taxPayerId: String): UserDataPojo
    suspend fun getEmail(email: String): UserDataPojo
    suspend fun getPassword(password: String) : UserDataPojo
}