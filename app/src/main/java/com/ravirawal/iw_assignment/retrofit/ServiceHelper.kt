package com.ravirawal.iw_assignment.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import com.ravirawal.iw_assignment.BuildConfig

const val READ_TIMEOUT = 90L
const val CONNECT_TIMEOUT = 60L

object ServiceHelper {

    private fun getOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getOkHttp())
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private fun getApiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }

    fun getAPIHelper(): IApiHelper {
        return IApiHelper.ApiHelperImpl(getApiService())
    }

}