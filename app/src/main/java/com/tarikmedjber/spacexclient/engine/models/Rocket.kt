package com.tarikmedjber.spacexclient.engine.models

import com.google.gson.annotations.SerializedName

data class Rocket(
    @SerializedName("rocket_name") val rocketName: String,
    @SerializedName("rocket_type") val rocketType: String
)
