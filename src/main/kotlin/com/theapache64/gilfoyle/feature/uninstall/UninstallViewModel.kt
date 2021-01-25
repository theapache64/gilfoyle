package com.theapache64.gilfoyle.feature.uninstall

import com.theapache64.cyclone.core.livedata.LiveData
import com.theapache64.cyclone.core.livedata.MutableLiveData
import com.theapache64.gilfoyle.data.local.Config
import com.theapache64.gilfoyle.data.repos.AdbRepo
import com.theapache64.gilfoyle.data.repos.ConfigRepo
import javax.inject.Inject

class UninstallViewModel @Inject constructor(
    private val adbRepo: AdbRepo,
    private val configRepo: ConfigRepo
) {
    companion object {

        val ACTION_UNINSTALL = "Uninstall"
        val ACTION_OPEN = "Open"

        val actions = listOf(
            ACTION_UNINSTALL,
            ACTION_OPEN
        )
    }

    private lateinit var config: Config
    private val _apps = MutableLiveData<List<String>>()
    val apps: LiveData<List<String>> = _apps

    fun init(device: String) {
        config = configRepo.config
        _apps.value = adbRepo.getInstalledApps(config, device)
    }

    fun onOpenAppSelected(app: String) {
        adbRepo.openApp(config, app)
    }

    fun onUninstallSelected(app: String) {

    }
}