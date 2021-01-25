package com.theapache64.gilfoyle.di.components

import com.theapache64.gilfoyle.feature.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}