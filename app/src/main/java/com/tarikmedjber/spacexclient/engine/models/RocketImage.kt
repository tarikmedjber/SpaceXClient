package com.tarikmedjber.spacexclient.engine.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RocketImage(
    @field:Json(name = "mission_patch_small") val missionImage: String
)
