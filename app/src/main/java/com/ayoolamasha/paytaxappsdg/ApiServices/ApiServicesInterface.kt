package com.ayoolamasha.paytaxappsdg.ApiServices

import com.ayoolamasha.paytaxappsdg.Login.LoginRequest
import com.ayoolamasha.paytaxappsdg.Login.LoginRequestResponse
import com.ayoolamasha.paytaxappsdg.Models.CalculateTaxRequest
import com.ayoolamasha.paytaxappsdg.Models.CalculateTaxResponse
import com.ayoolamasha.paytaxappsdg.Models.GetDataTypesResponse
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpRequest
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServicesInterface {
    @POST("/api/v2/users/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @POST("/api/v2/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginRequestResponse>

    @GET("/api/v2/payments/tax_types")
    suspend fun getTaxTypes(@Header("Authorization") accessToken:String): Response<GetDataTypesResponse>

    @POST("/api/v2/payments/payment_income_tax")
    suspend fun calculateTaxTypes(@Body calculateTaxRequest: CalculateTaxRequest): Response<CalculateTaxResponse>

}