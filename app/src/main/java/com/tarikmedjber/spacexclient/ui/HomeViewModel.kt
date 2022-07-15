package com.tarikmedjber.spacexclient.ui

import androidx.lifecycle.LiveData
import com.tarikmedjber.spacexclient.engine.models.CompanyInfo
import com.tarikmedjber.spacexclient.engine.models.Launches

interface HomeViewModel {

    sealed class State {
        object Loading : State()
        object Error : State()
        object Success : State()
    }

    val companyInfoState: LiveData<State>
    var companyInfo: CompanyInfo?
    fun getCompanyInfo()

    val launchesState: LiveData<State>
    var launchesList: List<Launches>?
    fun getLaunches(filteredOptions: FilteredOptions)

    fun filterLaunchesList(filteredOptions: FilteredOptions, launches: List<Launches>)
}