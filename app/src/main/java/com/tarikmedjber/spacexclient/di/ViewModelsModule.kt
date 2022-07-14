package com.tarikmedjber.spacexclient.di

import com.tarikmedjber.spacexclient.ui.HomeViewModel
import com.tarikmedjber.spacexclient.ui.HomeViewModelImpl
import org.koin.dsl.module

val viewModelsModule = module {
    single<HomeViewModel> { HomeViewModelImpl(get()) }
}