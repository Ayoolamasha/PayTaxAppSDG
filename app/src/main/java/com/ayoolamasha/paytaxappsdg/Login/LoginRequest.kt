package com.ayoolamasha.paytaxappsdg.Login

import com.google.gson.annotations.SerializedName

data class LoginPojoClass(

        @SerializedName("taxID")
        val taxID: String,

        @SerializedName("password")
        val password:String

)
