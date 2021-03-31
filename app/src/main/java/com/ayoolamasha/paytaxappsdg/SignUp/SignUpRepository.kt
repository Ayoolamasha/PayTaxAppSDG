package com.ayoolamasha.paytaxappsdg.SignUp

import android.app.Application
import android.content.Context
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServiceBuilder
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.Utils.RepositoryCallBack
import com.ayoolamasha.paytaxappsdg.Utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext


class SignUpRepository private constructor(application: Application) {

    private var context: Context
    private var apiServicesInterface: ApiServicesInterface
    private lateinit var modelSuccess: ApiResult.Success<SignUpResponse>
    private lateinit var modelError: ApiResult.Error<SignUpError>
    private val coroutineContext: CoroutineContext get() = Dispatchers.IO


    init {
        this.context = application.applicationContext
        apiServicesInterface = ApiServiceBuilder.buildService(ApiServicesInterface::class.java)
    }

    companion object :
        SingletonHolder<SignUpRepository, Application>(::SignUpRepository)


    suspend fun makeSignUpRequest(signUpRequest: SignUpRequest): ApiResult<Any> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiServicesInterface.signUp(signUpRequest)
                if (response.isSuccessful) {
                    if (response.body() != null && response.code() == 200) {
                        val signUpResponse = response.body()
                        return@withContext ApiResult.Success(signUpResponse)
                    } else if (response.code() != 200) {
                        return@withContext ApiResult.Error(response.body())
                    } else {
                        return@withContext ApiResult.NetworkError(true)
                    }
                } else {
                    return@withContext ApiResult.Error(response.errorBody())

                }

            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext ApiResult.Exception(t)
                } else {
                    throw t
                }
            }
        }


    }
}


