package com.theapache64.gilfoyle.di.components

import com.theapache64.gilfoyle.feature.uninstall.UninstallActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface UninstallComponent {
    fun inject(uninstallActivity: UninstallActivity)
}