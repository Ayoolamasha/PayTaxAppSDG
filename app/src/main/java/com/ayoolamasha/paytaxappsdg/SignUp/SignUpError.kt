package com.ayoolamasha.paytaxappsdg.SignUp

import com.google.gson.annotations.SerializedName

data class SignUpError(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("data")
    val data: String,
    @SerializedName("error")
    val signUpErrorPojo: SignUpErrorPojo
)