package com.tarikmedjber.spacexclient.ui.main

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
    var launches: Launches?
    fun getLaunches()
}