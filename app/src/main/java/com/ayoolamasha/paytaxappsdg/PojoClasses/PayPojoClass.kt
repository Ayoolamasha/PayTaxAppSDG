package com.ayoolamasha.paytaxappsdg.PojoClasses

import com.google.gson.annotations.SerializedName

data class PayPojoClass(
        @SerializedName("name")
        val name: String,
        @SerializedName("amount")
        val amount:String,
        @SerializedName("email")
        val email:String
)
