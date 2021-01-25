package com.theapache64.gilfoyle.feature.splash

import com.theapache64.cyclone.core.livedata.LiveData
import com.theapache64.cyclone.core.livedata.MutableLiveData
import com.theapache64.gilfoyle.data.repos.ConfigRepo
import java.io.File
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    configRepo: ConfigRepo
) {
    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String> = _showError

    private val _shouldMoveToDevices = MutableLiveData<Boolean>()
    val shouldMoveToDevices: LiveData<Boolean> = _shouldMoveToDevices


    init {
        configRepo.config.adbPath.let { adbFilePath ->
            val adbFile = File(adbFilePath)
            if (adbFile.exists()) {
                _shouldMoveToDevices.value = true
            } else {
                _showError.value = "ADB path '${adbFile.absolutePath}' doesn't exist"
            }
        }
    }
}