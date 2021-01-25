package com.theapache64.gilfoyle.di.components

import com.theapache64.gilfoyle.feature.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface UninstallComponent {
    fun inject(mainActivity: MainActivity)
}