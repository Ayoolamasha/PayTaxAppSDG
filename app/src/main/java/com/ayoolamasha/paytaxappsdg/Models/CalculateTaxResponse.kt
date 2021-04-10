package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class CalculateTaxResponse(

    @SerializedName("status")
    val status:Boolean,
    @SerializedName("data")
    val data: String,
    @SerializedName("message")
    val message:String
)
