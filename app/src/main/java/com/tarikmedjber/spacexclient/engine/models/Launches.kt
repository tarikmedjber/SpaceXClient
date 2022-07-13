package com.tarikmedjber.spacexclient.engine.models

import java.util.*

data class Launches(
    val missionName: String,
    val launchDate: Date,
    val rocket: Rocket,
    val links: RocketImage,
    val launchSuccess: Boolean
)
