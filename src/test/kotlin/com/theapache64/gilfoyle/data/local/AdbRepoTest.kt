package com.theapache64.gilfoyle.data.local

import com.theapache64.expekt.should
import com.theapache64.gilfoyle.data.repos.AdbRepo
import com.theapache64.gilfoyle.data.repos.ConfigRepo
import org.junit.Before
import org.junit.Test


class AdbRepoTest {

    private lateinit var configRepo: ConfigRepo
    private lateinit var adbRepo: AdbRepo

    @Before
    fun setup() {
        adbRepo = AdbRepo()
        configRepo = ConfigRepo()
    }

    @Test
    fun `Connected devices`() {
        adbRepo.getConnectedDevices(configRepo.config)
            .size.should.equal(1)
    }

    @Test
    fun `Installed apps`() {
        val config = configRepo.config
        adbRepo.getInstalledApps(
            config,
            adbRepo.getConnectedDevices(config).first()
        ).size.should.above(1)
    }

    @Test
    fun `Open app by package name`() {
        val config = configRepo.config
        adbRepo.openApp(
            config,
            "com.theapache64.topcorn"
        )
    }

}