package com.tarikmedjber.spacexclient.engine.models

import com.google.gson.annotations.SerializedName

data class RocketLinks(
    @SerializedName("mission_patch_small") val missionImage: String,
    @SerializedName("article_link") val articleLink: String,
    @SerializedName("wikipedia") val wikipediaLink: String,
    @SerializedName("video_link") val video_link: String
)
