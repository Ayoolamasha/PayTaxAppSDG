package com.ayoolamasha.paytaxappsdg.ApiServices

import com.ayoolamasha.paytaxappsdg.Login.LoginRequest
import com.ayoolamasha.paytaxappsdg.Login.LoginRequestResponse
import com.ayoolamasha.paytaxappsdg.Models.*
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpRequest
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpResponse
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import retrofit2.Response
import javax.inject.Inject

class ApiServiceInterfaceHelperImpl @Inject constructor(private val apiServicesInterface: ApiServicesInterface) : ApiServicesInterfaceHelper {
    override suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse>
    = apiServicesInterface.signUp(signUpRequest)

    override suspend fun login(loginRequest: LoginRequest): Response<LoginRequestResponse> = apiServicesInterface.login(loginRequest)

    override suspend fun getTaxTypes(accessToken: String): Response<GetTaxTypesResponse>{
        TODO("Not yet implemented")

    }

    override suspend fun calculateTaxTypes(calculateTaxRequest: CalculateTaxRequest): Response<CalculateTaxResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun makePaymentService(
        accessToken: String,
        paymentRequest: PaymentRequest
    ): Response<PaymentResponse> {
        TODO("Not yet implemented")
    }
}