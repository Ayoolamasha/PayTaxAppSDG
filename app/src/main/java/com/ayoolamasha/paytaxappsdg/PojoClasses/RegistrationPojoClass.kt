package com.ayoolamasha.paytaxappsdg.PojoClasses

import com.google.gson.annotations.SerializedName

data class RegistrationPojoClass(
        @SerializedName("name")
        val name: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("email")
        val email :String,
        @SerializedName("phone")
        val phone:String,
        @SerializedName("bvn")
        val bvn:String,
        @SerializedName("password")
        val password:String,
        @SerializedName("state")
        val state: String,
        @SerializedName("city")
        val city:String,
        @SerializedName("company")
        val company:String
)
