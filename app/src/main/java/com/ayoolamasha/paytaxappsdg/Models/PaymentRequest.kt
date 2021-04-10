package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("tax_type")
    val tax_type: String
)
