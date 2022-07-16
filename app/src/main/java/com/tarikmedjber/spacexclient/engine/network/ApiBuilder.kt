package com.tarikmedjber.spacexclient.engine.network

import android.content.Context
import com.tarikmedjber.spacexclient.BuildConfig.BASE_URL
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object ApiBuilder {

    fun buildApi(context: Context): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private fun getHttpClient(context: Context): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        return OkHttpClient.Builder().apply {
            cache(cache)
            addInterceptor(CacheInterceptor())
        }.build()
    }
}
