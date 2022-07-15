package com.tarikmedjber.spacexclient.engine.models

import com.google.gson.annotations.SerializedName

data class Launches(
    @SerializedName("mission_name") val missionName: String,
    @SerializedName("launch_date_utc") val launchDate: String,
    @SerializedName("rocket") val rocket: Rocket,
    @SerializedName("launch_success") val launchSuccess: Boolean,
    @SerializedName("links") val links: RocketLinks
)
