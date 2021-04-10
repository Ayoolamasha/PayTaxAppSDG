package com.ayoolamasha.paytaxappsdg.Models

import com.google.gson.annotations.SerializedName

data class GetComposite(

    @SerializedName("_id")
    val id:String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type:String

)
