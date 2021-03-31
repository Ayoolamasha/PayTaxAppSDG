package com.ayoolamasha.paytaxappsdg.ApiServices

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiServiceBuilder {

    companion object{
        val BASE_URL : String = "https://paytax-app.herokuapp.com/"

        lateinit var client: OkHttpClient

        lateinit var builder: Retrofit.Builder

        lateinit var retrofit:Retrofit


        fun <S> buildService(serviceType: Class<S>?): S {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)

            client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .build()
            builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
            retrofit = builder.build()

            return retrofit.create(serviceType)
        }
    }





}