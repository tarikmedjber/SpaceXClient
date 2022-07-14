package com.tarikmedjber.spacexclient.ui.main

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
    override var launches: Launches? = null

    override fun getCompanyInfo() {
        viewModelScope.launch {
            spaceXRepository.getCompanyInfo().collect { companyInfo ->
                companyInfoState.value = when (companyInfo) {
                    is RequestState.Failure -> {
                        HomeViewModel.State.Error
                    }
                    is RequestState.Loading -> {
                        HomeViewModel.State.Loading
                    }
                    is RequestState.Success -> {
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
                        HomeViewModel.State.Success
                    }
                }
            }
        }
    }
}