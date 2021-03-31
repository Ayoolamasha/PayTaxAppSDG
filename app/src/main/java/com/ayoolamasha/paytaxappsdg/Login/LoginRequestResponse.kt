package com.ayoolamasha.paytaxappsdg.Login

import com.google.gson.annotations.SerializedName

data class LoginRequestResponse(

    @SerializedName("status")
    val status: Boolean,

    val loginResponseData: LoginResponseData,

    @SerializedName("accessToken")
    val accessToken: String

)
