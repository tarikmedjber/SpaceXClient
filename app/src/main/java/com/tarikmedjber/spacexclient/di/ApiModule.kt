package com.tarikmedjber.spacexclient.di

import com.tarikmedjber.spacexclient.engine.network.*
import org.koin.dsl.binds
import org.koin.dsl.module

val apiModule = module {
    single { ApiBuilder.buildApi() } binds arrayOf(
        CompanyInfoService::class,
        LaunchesService::class
    )
    single<RequestExecutor> { RequestExecutorImpl() }
}