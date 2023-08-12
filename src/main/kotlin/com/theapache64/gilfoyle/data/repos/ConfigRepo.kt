package com.theapache64.gilfoyle.data.repos

import com.theapache64.gilfoyle.data.local.Config
import com.theapache64.gilfoyle.utils.JarUtils
import com.theapache64.gilfoyle.utils.JsonUtils
import kotlinx.serialization.decodeFromString
import java.io.File
import javax.inject.Inject

class ConfigRepo @Inject constructor() {

    val config: Config by lazy {
        val androidHome = System.getenv("ANDROID_HOME")
        val adbPath = "${androidHome}/platform-tools/adb"
        Config(adbPath = adbPath)
    }
}