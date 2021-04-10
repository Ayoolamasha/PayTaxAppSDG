package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class PaymentResponseComposite(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message:String
)
