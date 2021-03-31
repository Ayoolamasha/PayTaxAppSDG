package com.ayoolamasha.paytaxappsdg.Login

import com.google.gson.annotations.SerializedName

data class LoginRequest(

        @SerializedName("taxPayerId")
        val taxID: String,

        @SerializedName("password")
        val password:String

)
