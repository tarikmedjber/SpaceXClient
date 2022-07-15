package com.tarikmedjber.spacexclient.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikmedjber.spacexclient.engine.models.CompanyInfo
import com.tarikmedjber.spacexclient.engine.models.Launches
import com.tarikmedjber.spacexclient.engine.network.RequestState
import com.tarikmedjber.spacexclient.engine.repositories.SpaceXRepository
import kotlinx.coroutines.launch

class HomeViewModelImpl(private val spaceXRepository: SpaceXRepository) : ViewModel(),
    HomeViewModel {

    override val companyInfoState = MutableLiveData<HomeViewModel.State>()
    override val launchesState = MutableLiveData<HomeViewModel.State>()

    override var companyInfo: CompanyInfo? = null
    override var launchesList: List<Launches>? = null

    init {
        getCompanyInfo()
        getLaunches()
    }

    override fun getCompanyInfo() {
        viewModelScope.launch {
            spaceXRepository.getCompanyInfo().collect { companyInfoData ->
                companyInfoState.value = when (companyInfoData) {
                    is RequestState.Failure -> {
                        HomeViewModel.State.Error
                    }
                    is RequestState.Loading -> {
                        HomeViewModel.State.Loading
                    }
                    is RequestState.Success -> {
                        companyInfo = companyInfoData.data
                        HomeViewModel.State.Success
                    }
                }
            }
        }
    }

    override fun getLaunches() {
        viewModelScope.launch {
            spaceXRepository.getLaunches().collect { launches ->
                launchesState.value = when (launches) {
                    is RequestState.Failure -> {
                        HomeViewModel.State.Error
                    }
                    is RequestState.Loading -> {
                        HomeViewModel.State.Loading
                    }
                    is RequestState.Success -> {
                        if (launches.data.isEmpty()) {
                            HomeViewModel.State.Error
                        } else {
                            launchesList = launches.data
                            HomeViewModel.State.Success
                        }
                    }
                }
            }
        }
    }
}