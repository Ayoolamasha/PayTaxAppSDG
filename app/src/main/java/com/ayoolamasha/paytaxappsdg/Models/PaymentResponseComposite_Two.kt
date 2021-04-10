package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class PaymentResponseComposite_Two(

    @SerializedName("authorization_url")
    val authorization_url:String,
    @SerializedName("access_code")
    val access_code:String,
    @SerializedName("reference")
    val reference: String
)
