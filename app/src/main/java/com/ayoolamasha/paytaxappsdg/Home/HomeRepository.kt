package com.ayoolamasha.paytaxappsdg.Home

import android.app.Application
import android.content.Context
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServiceBuilder
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.Models.CalculateTaxRequest
import com.ayoolamasha.paytaxappsdg.Models.CalculateTaxResponse
import com.ayoolamasha.paytaxappsdg.Models.GetDataTypesResponse
import com.ayoolamasha.paytaxappsdg.Utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

class HomeRepository private constructor(application: Application){
    private lateinit var context: Context

    private lateinit var apiServicesInterface: ApiServicesInterface
    private var accessTokenFig: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MTgwNDkxNjcsImV4cCI6MTYxODEzNTU2N30.XF3N1s8jg7UiXM0EfIb_VMZt0eJbk4c5CWCu71v9AmA"


    private val coroutineContext: CoroutineContext get() = Dispatchers.IO

    companion object:
            SingletonHolder<HomeRepository, Application>(::HomeRepository)


    init {
        this.context = application.applicationContext
        apiServicesInterface = ApiServiceBuilder.buildService(ApiServicesInterface::class.java)
    }

    suspend fun getTaxType(): ApiResult<Any>{
        return withContext(Dispatchers.IO){
            try {
                val response = apiServicesInterface.getTaxTypes("Bearer $accessTokenFig")
                if (response.isSuccessful){
                    if (response.body() !=null && response.code() == 200){
                        val getTaxTypesResponse = response.body()
                        return@withContext ApiResult.Success(getTaxTypesResponse)
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

    suspend fun makeCalculateRequest(calculateTaxRequest: CalculateTaxRequest): ApiResult<Any>{
        return withContext(Dispatchers.IO){
            try {
                val response = apiServicesInterface.calculateTaxTypes(calculateTaxRequest)
                if (response.isSuccessful){
                    if (response.body() != null && response.code() == 200){
                        val calculateResponse = response.body()
                        return@withContext ApiResult.Success(calculateResponse)
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