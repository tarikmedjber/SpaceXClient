package com.tarikmedjber.spacexclient.engine.network

import com.tarikmedjber.spacexclient.engine.models.CompanyInfo
import com.tarikmedjber.spacexclient.engine.models.Launches
import retrofit2.Response
import retrofit2.http.GET

interface Api : CompanyInfoService, LaunchesService

interface CompanyInfoService {
    @GET("info")
    suspend fun getCompanyInfo(): Response<CompanyInfo>
}

interface LaunchesService {
    @GET("launches")
    suspend fun getLaunches(): Response<Launches>
}