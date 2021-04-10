package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class CalculateTaxRequest(
    @SerializedName("taxPayerId")
    val taxId: String,
    @SerializedName("income")
    val income: String
)
