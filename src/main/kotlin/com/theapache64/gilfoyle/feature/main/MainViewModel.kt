package com.theapache64.gilfoyle.feature.main

import com.theapache64.cyclone.core.livedata.LiveData
import com.theapache64.cyclone.core.livedata.MutableLiveData
import com.theapache64.gilfoyle.data.local.Cache
import com.theapache64.gilfoyle.data.local.Config
import com.theapache64.gilfoyle.data.repos.AdbRepo
import com.theapache64.gilfoyle.data.repos.CacheRepo
import com.theapache64.gilfoyle.data.repos.ConfigRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val adbRepo: AdbRepo,
    private val configRepo: ConfigRepo,
    private val cacheRepo: CacheRepo
) {
    companion object {

        const val ACTION_SKIP = "Skip"
        const val ACTION_UNINSTALL = "Uninstall"
        const val ACTION_OPEN = "Open"

        val actions = listOf(
            ACTION_SKIP,
            ACTION_UNINSTALL,
            ACTION_OPEN
        )
    }

    private lateinit var cache: Cache
    private lateinit var device: String
    private lateinit var config: Config

    private val _apps = MutableLiveData<List<String>>()
    val apps: LiveData<List<String>> = _apps

    private val _showOpenFailed = MutableLiveData<String>()
    val showOpenFailed: LiveData<String> = _showOpenFailed

    private val _showUninstallFailed = MutableLiveData<String>()
    val showUninstallFailed: LiveData<String> = _showUninstallFailed

    fun init(device: String) {
        config = configRepo.config
        this.device = device
        this.cache = cacheRepo.cache
        _apps.value = adbRepo.getInstalledApps(config, device)
            .filter { !cache.analyzedApps.contains(it) } // apps that are not in cache
    }

    fun onOpenAppSelected(app: String): Boolean {
        val isOpened = adbRepo.openApp(config, device, app)
        if (!isOpened) {
            _showOpenFailed.value = app
        }
        return isOpened
    }

    fun onUninstallSelected(app: String): Boolean {
        val isUninstalled = adbRepo.uninstallApp(config, device, app)
        if (isUninstalled.not()) {
            _showUninstallFailed.value = app
        }
        return isUninstalled
    }

    fun onAnalyzed(app: String) {
        // add to cache
        cache.analyzedApps.add(app)
        cacheRepo.saveCache(cache)
    }
}