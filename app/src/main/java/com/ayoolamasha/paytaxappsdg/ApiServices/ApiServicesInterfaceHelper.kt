package com.ayoolamasha.paytaxappsdg.ApiServices

import com.ayoolamasha.paytaxappsdg.Login.LoginRequest
import com.ayoolamasha.paytaxappsdg.Login.LoginRequestResponse
import com.ayoolamasha.paytaxappsdg.Models.*
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpRequest
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

interface ApiServicesInterfaceHelper {
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginRequestResponse>

    suspend fun getTaxTypes(@Header("Authorization") accessToken:String): Response<GetTaxTypesResponse>

    suspend fun calculateTaxTypes(@Body calculateTaxRequest: CalculateTaxRequest): Response<CalculateTaxResponse>

    suspend fun makePaymentService(@Header("Authorization") accessToken:String,  @Body paymentRequest: PaymentRequest): Response<PaymentResponse>

}