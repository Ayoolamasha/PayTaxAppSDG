package com.ayoolamasha.paytaxappsdg.DI.Module


import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServiceInterfaceHelperImpl
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterface
import com.ayoolamasha.paytaxappsdg.ApiServices.ApiServicesInterfaceHelper
import com.ayoolamasha.paytaxappsdg.BuildConfig
import com.ayoolamasha.paytaxappsdg.UserData.UserDao
import com.ayoolamasha.paytaxappsdg.UserData.UserDataHelper
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val SHARED_PREFERENCE_NAME = "pay_tax_app_sdg_prefs"

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    private const val BASE_URL : String = "https://paytax-app.herokuapp.com/"
    private const val DATABASE_NAME:String = "user_data"

    @Provides
    fun providesBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun providesOkHttpClient() = if (BuildConfig.DEBUG){
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .build()
    }else OkHttpClient
        .Builder()
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiServicesInterface = retrofit.create(ApiServicesInterface::class.java)

    @Singleton
    @Provides
    fun providesApiHelper(apiServiceInterfaceHelperImpl: ApiServiceInterfaceHelperImpl): ApiServicesInterfaceHelper = apiServiceInterfaceHelperImpl

    @Singleton
    @Provides
    fun providesRoomDataBaser(@ApplicationContext appContext: Context)= Room.databaseBuilder(
        appContext, UserDataRoomDatabase::class.java, DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesUserDaoBaseHelper(userDataRoomDatabase: UserDataRoomDatabase): UserDao = userDataRoomDatabase.userDao()

    @Singleton
    @Provides
    fun providesSharedPref(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)







}