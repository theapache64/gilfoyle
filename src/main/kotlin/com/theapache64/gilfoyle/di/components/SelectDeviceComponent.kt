package com.theapache64.gilfoyle.di.components

import com.theapache64.gilfoyle.feature.device.SelectDeviceActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface SelectDeviceComponent {
    fun inject(selectDeviceActivity: SelectDeviceActivity)
}