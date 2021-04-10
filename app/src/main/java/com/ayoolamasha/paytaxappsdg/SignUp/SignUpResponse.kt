package com.ayoolamasha.paytaxappsdg.SignUp

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("data")
    val signUpResponseData: SignUpResponseData?,

    @SerializedName("accesstoken")
    val accessToken: String

)