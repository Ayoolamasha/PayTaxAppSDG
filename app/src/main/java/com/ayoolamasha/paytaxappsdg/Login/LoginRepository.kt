package com.ayoolamasha.paytaxappsdg.Login

import android.app.Application
import android.content.Context
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServiceBuilder
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterfaceHelper
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpRepository
//import com.ayoolamasha.paytaxappsdg.UserData.UserDaoHelper
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.Utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Response
import java.util.concurrent.CancellationException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginRepository @Inject constructor(private val apiServicesInterfaceHelper: ApiServicesInterfaceHelper
){
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginRequestResponse> = apiServicesInterfaceHelper.login(loginRequest)

}

