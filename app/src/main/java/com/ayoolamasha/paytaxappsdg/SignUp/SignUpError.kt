package com.ayoolamasha.paytaxappsdg.SignUp

import com.google.gson.annotations.SerializedName

data class SignUpError(
    @SerializedName("status")
    val status: Boolean,
    val signUpErrorPojo: SignUpErrorPojo
)