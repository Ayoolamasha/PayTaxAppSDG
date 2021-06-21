package com.ayoolamasha.paytaxappsdg.UserData

import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("taxPayerId")
    val taxPayerId: String,
    @SerializedName("_id")
    val _id: String
)
