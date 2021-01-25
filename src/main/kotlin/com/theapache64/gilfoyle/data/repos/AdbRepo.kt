package com.theapache64.gilfoyle.data.repos

import com.theapache64.gilfoyle.data.local.Config
import com.theapache64.gilfoyle.utils.CommandExecutor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdbRepo @Inject constructor() {
    /**
     * To get list of currently connected devices
     */
    fun getConnectedDevices(config: Config): List<String> {
        val output = CommandExecutor.executeCommand("${config.adbPath} devices")
        val lines = output.split("\n")
        val deviceCount = lines.size - 2 // -2 = Heading + last label
        val deviceList = mutableListOf<String>()
        if (deviceCount > 0) {
            for (i in 1 until lines.size - 1) {
                val deviceName = lines[i].replace("\tdevice", "").trim()
                deviceList.add(deviceName)
            }
        }
        return deviceList
    }

    /**
     * To get installed apps' package names
     */
    fun getInstalledApps(config: Config, device: String): List<String> {
        val output = CommandExecutor.executeCommand(
            "${config.adbPath} -s '$device' shell pm list packages -3"
        )

        val apps = mutableListOf<String>()
        val lines = output.split("\n")
        for (line in lines) {
            apps.add(line.replace("package:", "").trim())
        }
        return apps
    }

    /**
     * To launch app with the given package name
     */
    fun openApp(config: Config, device: String, packageName: String): Boolean {
        val output = CommandExecutor.executeCommand(
            "${config.adbPath} -s '$device' shell monkey -p $packageName 1",
            isLivePrint = false,
            isSkipException = true
        )

        return output.contains("Events injected: 1")
    }

    /**
     * To uninstall app from device
     */
    fun uninstallApp(config: Config, device: String, packageName: String): Boolean {
        val command = "${config.adbPath} -s '$device' uninstall '$packageName'"
        val output = CommandExecutor.executeCommand(
            command,
            isLivePrint = false,
            isSkipException = true
        )

        return output == "Success"
    }

}