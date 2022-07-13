package com.tarikmedjber.spacexclient.di

import com.tarikmedjber.spacexclient.engine.network.ApiBuilder
import com.tarikmedjber.spacexclient.engine.network.CompanyInfoService
import com.tarikmedjber.spacexclient.engine.network.LaunchesService
import org.koin.dsl.binds
import org.koin.dsl.module

val apiModule = module {
    single { ApiBuilder.buildApi() } binds arrayOf(
        CompanyInfoService::class,
        LaunchesService::class
    )
}