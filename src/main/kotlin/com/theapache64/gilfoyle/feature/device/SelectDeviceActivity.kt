package com.theapache64.gilfoyle.feature.device

import com.theapache64.cyclone.core.Activity
import com.theapache64.cyclone.core.Intent
import com.theapache64.gilfoyle.di.components.DaggerSelectDeviceComponent
import com.theapache64.gilfoyle.feature.main.MainActivity
import com.yg.kotlin.inquirer.components.promptList
import com.yg.kotlin.inquirer.core.KInquirer
import javax.inject.Inject

class SelectDeviceActivity : Activity() {
    companion object {
        fun getStartIntent(): Intent {
            return Intent(to = SelectDeviceActivity::class)
        }
    }

    @Inject
    lateinit var viewModel: SelectDeviceViewModel

    override fun onCreate() {
        super.onCreate()
        DaggerSelectDeviceComponent.builder().build().inject(this)

        // Watching for no device error
        viewModel.shouldShowNoDeviceConnected.observe { shouldShow ->
            if (shouldShow) {
                println("No device connected")
            }
        }

        // Watching for devices
        viewModel.devices.observe { devices ->
            val selectedDevice = if (devices.size == 1) {
                // Go with the first one
                devices.first()
            } else {
                // Prompt for multiple devices
                KInquirer.promptList(
                    "Select the device: ",
                    devices
                )
            }

            viewModel.onDeviceSelected(selectedDevice)
        }

        // Watching for uninstall activity launch
        viewModel.launchUninstallActivity.observe { device ->
            startActivity(MainActivity.getStartIntent(device))
        }
    }
}