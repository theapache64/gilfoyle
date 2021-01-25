package com.theapache64.gilfoyle.data.repos

import com.theapache64.expekt.should
import org.junit.Test


class ConfigRepoTest {
    @Test
    fun `Adb path`() {
        ConfigRepo().config.adbPath
            .should.endWith("/adb")
    }
}