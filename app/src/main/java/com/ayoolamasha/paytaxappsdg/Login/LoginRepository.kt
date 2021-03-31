package com.ayoolamasha.paytaxappsdg.Login

import android.app.Application
import android.content.Context
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServiceBuilder
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpRepository
import com.ayoolamasha.paytaxappsdg.Utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

class LoginRepository private constructor(application: Application){

    private var context: Context
    private lateinit var apiServicesInterface: ApiServicesInterface
    private val coroutineContext: CoroutineContext get() = Dispatchers.IO

    companion object :
        SingletonHolder<LoginRepository, Application>(::LoginRepository)



    init {
            this.context = application.applicationContext
             apiServicesInterface = ApiServiceBuilder.buildService(ApiServicesInterface::class.java)
    }

    suspend fun makeLoginRequest(loginRequest: LoginRequest): ApiResult<Any>{
       return withContext(Dispatchers.IO){
           try {

               val response = apiServicesInterface.login(loginRequest)
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