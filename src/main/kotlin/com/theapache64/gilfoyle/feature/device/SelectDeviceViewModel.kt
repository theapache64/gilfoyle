package com.theapache64.gilfoyle.feature.device

import com.theapache64.cyclone.core.livedata.LiveData
import com.theapache64.cyclone.core.livedata.MutableLiveData
import com.theapache64.gilfoyle.data.repos.AdbRepo
import com.theapache64.gilfoyle.data.repos.ConfigRepo
import javax.inject.Inject

class SelectDeviceViewModel @Inject constructor(
    private val configRepo: ConfigRepo,
    private val adbRepo: AdbRepo
) {

    private val _devices = MutableLiveData<List<String>>()
    val devices: LiveData<List<String>> = _devices

    private val _shouldShowNoDeviceConnected = MutableLiveData<Boolean>()
    val shouldShowNoDeviceConnected: LiveData<Boolean> = _shouldShowNoDeviceConnected

    private val _launchUninstallActivity = MutableLiveData<String>()
    val launchUninstallActivity: LiveData<String> = _launchUninstallActivity

    init {
        val config = configRepo.config
        val devices = adbRepo.getConnectedDevices(config)

        if (devices.isEmpty()) {
            // show no device error
            _shouldShowNoDeviceConnected.value = true
        } else {
            // devices available
            _devices.value = devices
        }
    }


    /**
     * Invoked when device selected
     */
    fun onDeviceSelected(device: String) {
        _launchUninstallActivity.value = device
    }
}