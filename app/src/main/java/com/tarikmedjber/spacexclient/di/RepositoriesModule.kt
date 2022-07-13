package com.tarikmedjber.spacexclient.di

import com.tarikmedjber.spacexclient.engine.repositories.SpaceXRepository
import com.tarikmedjber.spacexclient.engine.repositories.SpaceXRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<SpaceXRepository> { SpaceXRepositoryImpl(get(), get(), get()) }
}