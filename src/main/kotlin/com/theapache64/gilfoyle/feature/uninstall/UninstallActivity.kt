package com.theapache64.gilfoyle.feature.uninstall


import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.TextColors.green
import com.github.ajalt.mordant.terminal.TextColors.red
import com.theapache64.cyclone.core.Activity
import com.theapache64.cyclone.core.Intent
import com.theapache64.gilfoyle.di.components.DaggerUninstallComponent
import com.yg.kotlin.inquirer.components.promptList
import com.yg.kotlin.inquirer.core.KInquirer
import javax.inject.Inject

class UninstallActivity : Activity() {

    companion object {
        private const val KEY_DEVICE = "device"
        fun getStartIntent(device: String): Intent {
            return Intent(to = UninstallActivity::class).apply {
                putExtra(KEY_DEVICE, device)
            }
        }
    }

    @Inject
    lateinit var viewModel: UninstallViewModel

    override fun onCreate() {
        super.onCreate()
        DaggerUninstallComponent.builder().build().inject(this)

        val device = getStringExtra(KEY_DEVICE)
        viewModel.init(device)
        val terminal = Terminal()

        viewModel.apps.observe { apps ->
            if (apps.isNotEmpty()) {
                var uninstallCount = 0
                for (app in apps) {
                    var action: String
                    do {
                        action = KInquirer.promptList(
                            "Package name : ${green(app)}",
                            UninstallViewModel.actions
                        )
                        when (action) {
                            UninstallViewModel.ACTION_OPEN -> {
                                // open app
                                viewModel.onOpenAppSelected(app)
                            }

                            UninstallViewModel.ACTION_UNINSTALL -> {
                                // uninstall app
                                viewModel.onUninstallSelected(app)
                                uninstallCount++
                            }
                        }
                    } while (action == UninstallViewModel.ACTION_OPEN)

                    if (uninstallCount > 0) {
                        terminal.print(green("$uninstallCount app(s) uninstalled"))
                    }
                }
            } else {
                // no apps
                terminal.println(red("No apps found in '$device'"))
            }
        }
    }
}