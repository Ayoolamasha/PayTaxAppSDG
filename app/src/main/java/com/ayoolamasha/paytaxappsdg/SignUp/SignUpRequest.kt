package com.ayoolamasha.paytaxappsdg.SignUp

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String
)
