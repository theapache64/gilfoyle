package com.theapache64.gilfoyle.di.components

import com.theapache64.gilfoyle.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface GilfoyleComponent {
    fun inject(app: App)
}