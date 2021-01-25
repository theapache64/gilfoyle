package com.theapache64.gilfoyle.data.repos

import com.theapache64.gilfoyle.data.local.Cache
import com.theapache64.gilfoyle.utils.JarUtils
import com.theapache64.gilfoyle.utils.JsonUtils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File
import javax.inject.Inject

class CacheRepo @Inject constructor() {

    companion object {
        private const val DEFAULT_CACHE_CONTENT = """
            {
              "analyzed_apps": []
            }
        """
    }

    private val cacheFile by lazy {
        File("${JarUtils.getJarDir()}cache.json")
    }

    val cache: Cache by lazy {
        if (cacheFile.exists().not()) {
            cacheFile.writeText(DEFAULT_CACHE_CONTENT)
        }
        cacheFile.readText().let { cacheJson ->
            JsonUtils.json.decodeFromString(cacheJson)
        }
    }

    fun saveCache(cache: Cache) {
        val cacheJson = JsonUtils.json.encodeToString(cache)
        cacheFile.writeText(cacheJson)
    }
}