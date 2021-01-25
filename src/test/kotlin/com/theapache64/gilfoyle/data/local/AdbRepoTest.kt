package com.theapache64.gilfoyle.data.local

import com.theapache64.expekt.should
import com.theapache64.gilfoyle.data.repos.AdbRepo
import com.theapache64.gilfoyle.data.repos.ConfigRepo
import org.junit.Before
import org.junit.Test


class AdbRepoTest {

    private lateinit var adbRepo: AdbRepo
    private lateinit var config: Config
    private lateinit var device: String

    @Before
    fun setup() {
        adbRepo = AdbRepo()
        config = ConfigRepo().config
        device = adbRepo.getConnectedDevices(config).first()
    }

    @Test
    fun `Connected devices`() {
        adbRepo.getConnectedDevices(config)
            .size.should.equal(1)
    }

    @Test
    fun `Installed apps`() {
        adbRepo.getInstalledApps(
            config,
            adbRepo.getConnectedDevices(config).first()
        ).size.should.above(1)
    }

    @Test
    fun `Open valid app by package name`() {
        adbRepo.openApp(
            config,
            device,
            "com.android.chrome"
        ).should.`true`
    }

    @Test
    fun `Open invalid app by package name`() {
        adbRepo.openApp(
            config,
            device,
            "this.is.a.invalid.package.name"
        ).should.`false`
    }

    @Test
    fun `Uninstall app`() {
        adbRepo.uninstallApp(
            config,
            device,
            "com.theapache64.topcorn2"
        ).let {
            println("Result $it")
        }
    }

}