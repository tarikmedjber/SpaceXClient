package com.tarikmedjber.spacexclient.engine.models

import com.google.gson.annotations.SerializedName

data class CompanyInfo(
    @SerializedName("name") val name: String,
    @SerializedName("founder") val founder: String,
    @SerializedName("founded") val founded: Int,
    @SerializedName("employees") val employees: Int,
    @SerializedName("launch_sites") val launchSites: Int,
    @SerializedName("valuation") val valuation: Long
)
