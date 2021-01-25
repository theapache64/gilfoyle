package com.theapache64.gilfoyle.data.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerialName("adb_path")
    val adbPath: String // /home/theapache64/Android/Sdk/platform-tools/adb
)