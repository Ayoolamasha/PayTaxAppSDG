package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class PaymentResponse(

    @SerializedName("status")
    val status:Boolean,
    @SerializedName("data")
    val paymentResponseComposite: PaymentResponseComposite,
    @SerializedName("data")
    val paymentresponsecompositeTwo: PaymentResponseComposite_Two

)
