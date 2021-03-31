package com.ayoolamasha.paytaxappsdg.SignUp

import com.google.gson.annotations.SerializedName

data class SignUpErrorPojo(
    @SerializedName("msg")
    val message: String,
    @SerializedName("param")
    val param: String,
    @SerializedName("location")
    val location: String
)