package com.tarikmedjber.spacexclient.engine.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyInfo(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "founder") val founder: String,
    @field:Json(name = "founded") val founded: Int,
    @field:Json(name = "employees") val employees: Int,
    @field:Json(name = "launch_sites") val launchSites: Int,
    @field:Json(name = "valuation") val valuation: Long
)
