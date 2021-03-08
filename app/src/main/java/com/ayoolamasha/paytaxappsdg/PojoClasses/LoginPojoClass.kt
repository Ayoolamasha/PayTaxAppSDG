package com.ayoolamasha.paytaxappsdg.PojoClasses

import com.google.gson.annotations.SerializedName

data class LoginPojoClass(

        @SerializedName("taxID")
        val taxID: String,

        @SerializedName("password")
        val password:String

)
