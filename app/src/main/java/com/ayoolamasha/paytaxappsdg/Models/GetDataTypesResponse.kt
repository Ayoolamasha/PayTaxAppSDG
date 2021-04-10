package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class GetDataTypesResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("data")
    val getComposite: List<GetComposite>

)
