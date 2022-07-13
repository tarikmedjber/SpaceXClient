package com.tarikmedjber.spacexclient.engine.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rocket(
    @field:Json(name = "rocket_name") val rocketName: String,
    @field:Json(name = "rocket_type") val rocketType: String
)
