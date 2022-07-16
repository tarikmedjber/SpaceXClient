package com.tarikmedjber.spacexclient.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tarikmedjber.spacexclient.engine.models.CompanyInfo
import com.tarikmedjber.spacexclient.engine.models.Launches
import com.tarikmedjber.spacexclient.engine.network.RequestState
import com.tarikmedjber.spacexclient.engine.repositories.SpaceXRepository
import kotlinx.coroutines.launch
import java.util.logging.Filter

class HomeViewModelImpl(private val spaceXRepository: SpaceXRepository) : ViewModel(),
    HomeViewModel {

    override val companyInfoState = MutableLiveData<HomeViewModel.State>()
    override val launchesState = MutableLiveData<HomeViewModel.State>()

    override var companyInfo: CompanyInfo? = null
    override var launchesList: List<Launches>? = null

    init {
        getCompanyInfo()
        getLaunches(FilteredOptions.Date(OrderType.Descending))
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

    override fun getLaunches(filteredOptions: FilteredOptions) {
        viewModelScope.launch {
            spaceXRepository.getLaunches().collect { launches ->
                when (launches) {
                    is RequestState.Failure -> {
                        launchesState.value = HomeViewModel.State.Error
                    }
                    is RequestState.Loading -> {
                        launchesState.value = HomeViewModel.State.Loading
                    }
                    is RequestState.Success -> {
                        if (launches.data.isEmpty()) {
                            launchesState.value = HomeViewModel.State.Error
                        } else {
                            filterLaunchesList(filteredOptions, launches.data)
                            launchesState.value = HomeViewModel.State.Success
                        }
                    }
                }
            }
        }
    }

    override fun filterLaunchesList(
        filteredOptions: FilteredOptions,
        launches: List<Launches>
    ) {
        val newLaunches  = when (filteredOptions.orderType) {
            is OrderType.Ascending -> {
                when (filteredOptions) {
                    is FilteredOptions.Date -> launches.sortedBy { it.launchDate }
                    is FilteredOptions.LaunchSuccess -> launches.sortedBy { it.launchSuccess }
                }
            }
            is OrderType.Descending -> {
                when (filteredOptions) {
                    is FilteredOptions.Date -> launches.sortedByDescending { it.launchDate }
                    is FilteredOptions.LaunchSuccess -> launches.sortedByDescending { it.launchSuccess }
                }
            }
        }
        launchesList = newLaunches
        launchesState.value = HomeViewModel.State.Success
    }
}