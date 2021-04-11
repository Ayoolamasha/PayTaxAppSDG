package com.ayoolamasha.paytaxappsdg.Payment

import android.app.Application
import android.content.Context
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServiceBuilder
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.Login.LoginRequest
import com.ayoolamasha.paytaxappsdg.Models.PaymentRequest
import com.ayoolamasha.paytaxappsdg.Utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

class MakePaymentRepository private constructor(application: Application){
    private val context: Context
    private lateinit var apiServicesInterface: ApiServicesInterface
    private val coroutineContext: CoroutineContext  get() = Dispatchers.IO

    private var toke: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MTgwNDkxNjcsImV4cCI6MTYxODEzNTU2N30.XF3N1s8jg7UiXM0EfIb_VMZt0eJbk4c5CWCu71v9AmA"


    companion object:
            SingletonHolder<MakePaymentRepository, Application>(::MakePaymentRepository)


    init {
        this.context = application.applicationContext
        apiServicesInterface = ApiServiceBuilder.buildService(ApiServicesInterface::class.java)

    }

    suspend fun makeLoginRequest(paymentRequest: PaymentRequest): ApiResult<Any> {
        return withContext(Dispatchers.IO){
            try {

                val response = apiServicesInterface.makePaymentService("Bearer $toke", paymentRequest )
                if (response.isSuccessful){
                    if (response.body() != null && response.code()==200){
                        val loginResponse = response.body()
                        return@withContext ApiResult.Success(loginResponse)
                    }else if(response.code() != 200){
                        return@withContext ApiResult.Error(response.message())

                    }else{
                        return@withContext ApiResult.NetworkError(true)
                    }
                }else{
                    return@withContext ApiResult.Error(response.errorBody())
                }
            }catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext ApiResult.Exception(t)
                } else {
                    throw t
                }
            }
        }
    }
}