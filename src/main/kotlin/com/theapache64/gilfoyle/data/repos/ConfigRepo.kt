package com.theapache64.gilfoyle.data.repos

import com.theapache64.gilfoyle.data.local.Config
import com.theapache64.gilfoyle.utils.JsonUtils
import kotlinx.serialization.decodeFromString
import java.io.File
import javax.inject.Inject

class ConfigRepo @Inject constructor() {
    val config: Config by lazy {
        File("config.json").readText().let { configJsonString ->
            JsonUtils.json.decodeFromString(configJsonString)
        }
    }
}