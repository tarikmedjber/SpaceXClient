package com.tarikmedjber.spacexclient.engine.models

import com.google.gson.annotations.SerializedName

data class RocketImage(
    @SerializedName("mission_patch_small") val missionImage: String
)
