package com.tarikmedjber.spacexclient.di

import com.tarikmedjber.spacexclient.ui.main.HomeViewModel
import com.tarikmedjber.spacexclient.ui.main.HomeViewModelImpl
import org.koin.dsl.module

val viewModelsModule = module {
    single<HomeViewModel> { HomeViewModelImpl(get()) }
}