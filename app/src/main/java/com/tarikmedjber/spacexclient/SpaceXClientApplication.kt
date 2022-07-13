package com.tarikmedjber.spacexclient

import android.app.Application
import com.tarikmedjber.spacexclient.di.apiModule
import com.tarikmedjber.spacexclient.di.repositoriesModule
import com.tarikmedjber.spacexclient.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceXClientApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SpaceXClientApplication)
            modules(
                viewModelsModule,
                repositoriesModule,
                apiModule
            )
        }
    }
}