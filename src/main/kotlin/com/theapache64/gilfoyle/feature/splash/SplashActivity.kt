package com.theapache64.gilfoyle.feature.splash

import com.theapache64.cyclone.core.Activity
import com.theapache64.cyclone.core.Intent
import com.theapache64.gilfoyle.di.components.DaggerSplashComponent
import com.theapache64.gilfoyle.feature.device.SelectDeviceActivity
import javax.inject.Inject

class SplashActivity : Activity() {

    companion object {
        fun getStartIntent(): Intent {
            return Intent(to = SplashActivity::class)
        }
    }

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate() {
        super.onCreate()
        DaggerSplashComponent.builder().build().inject(this)

        viewModel.showError.observe {
            println("Error -> $it")
        }

        viewModel.shouldMoveToDevices.observe { shouldMoveToDevices ->
            if (shouldMoveToDevices) {
                startActivity(SelectDeviceActivity.getStartIntent())
            }
        }
    }
}