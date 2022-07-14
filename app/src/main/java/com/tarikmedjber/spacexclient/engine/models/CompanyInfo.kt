package com.tarikmedjber.spacexclient.engine.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyInfo(
    @Json(name = "name") val name: String,
    @Json(name = "founder") val founder: String,
    @Json(name = "founded") val founded: Int,
    @Json(name = "employees") val employees: Int,
    @Json(name = "launch_sites") val launchSites: Int,
    @Json(name = "valuation") val valuation: Long
)
