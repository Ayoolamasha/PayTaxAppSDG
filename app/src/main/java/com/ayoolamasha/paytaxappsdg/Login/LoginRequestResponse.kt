package com.ayoolamasha.paytaxappsdg.Login

import com.google.gson.annotations.SerializedName

data class LoginRequestResponse(

    @SerializedName("status")
    val status: Boolean,

    @SerializedName("data")
    val loginResponseData: LoginResponseData,

    @SerializedName("accesstoken")
    val accessToken: String

)
