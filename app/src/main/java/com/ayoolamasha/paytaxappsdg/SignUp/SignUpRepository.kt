package com.ayoolamasha.paytaxappsdg.SignUp

import android.app.Application
import android.content.Context
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServiceBuilder
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterfaceHelper
import com.ayoolamasha.paytaxappsdg.Utils.RepositoryCallBack
import com.ayoolamasha.paytaxappsdg.Utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CancellationException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SignUpRepository @Inject constructor(private val apiServicesInterfaceHelper: ApiServicesInterfaceHelper){
    suspend fun signUpUser(signUpRequest: SignUpRequest): Response<SignUpResponse>
    = apiServicesInterfaceHelper.signUp(signUpRequest)
}




