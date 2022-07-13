package com.tarikmedjber.spacexclient.engine.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Launches(
    @field:Json(name = "mission_name") val missionName: String,
    @field:Json(name = "launch_date_utc") val launchDate: String,
    @field:Json(name = "rocket") val rocket: Rocket,
    @field:Json(name = "links") val links: RocketImage,
    @field:Json(name = "launch_success") val launchSuccess: Boolean
)
