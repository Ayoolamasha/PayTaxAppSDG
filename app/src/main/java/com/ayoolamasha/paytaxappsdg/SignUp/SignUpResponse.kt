package com.ayoolamasha.paytaxappsdg.SignUp

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    val status: Boolean,

    val signUpResponseData: SignUpResponseData?,

    @SerializedName("accessToken")
    val accessToken: String

)