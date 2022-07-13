package com.tarikmedjber.spacexclient.engine.repositories

import com.tarikmedjber.spacexclient.engine.models.CompanyInfo
import com.tarikmedjber.spacexclient.engine.models.Launches
import com.tarikmedjber.spacexclient.engine.network.RequestState
import kotlinx.coroutines.flow.Flow

interface SpaceXRepository {
    suspend fun getCompanyInfo(): Flow<RequestState<CompanyInfo>>
    suspend fun getLaunches(): Flow<RequestState<List<Launches>>>
}