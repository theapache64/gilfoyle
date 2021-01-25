package com.theapache64.gilfoyle.data.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cache(
    @SerialName("analyzed_apps")
    val analyzedApps: MutableList<String>
)