package com.tarikmedjber.spacexclient.engine.network

import com.tarikmedjber.spacexclient.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiBuilder {

    fun buildApi(): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            val requestWithUserAgent = chain.request().newBuilder().build()
            chain.proceed(requestWithUserAgent)
        }
        return okHttpBuilder.build()
    }

}