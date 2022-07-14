package com.tarikmedjber.spacexclient.engine.repositories

import com.tarikmedjber.spacexclient.engine.network.CompanyInfoService
import com.tarikmedjber.spacexclient.engine.network.LaunchesService
import com.tarikmedjber.spacexclient.engine.network.RequestExecutor
import kotlinx.coroutines.flow.flow

class SpaceXRepositoryImpl(
    private val companyInfoService: CompanyInfoService,
    private val launchesService: LaunchesService,
    private val requestExecutor: RequestExecutor
) : SpaceXRepository {

    override suspend fun getCompanyInfo() = flow {
        requestExecutor.performRequest {
            companyInfoService.getCompanyInfo()
        }.collect {
            emit(it)
        }
    }

    override suspend fun getLaunches() = flow {
        requestExecutor.performRequest {
            launchesService.getLaunches()
        }.collect {
            emit(it)
        }
    }

}