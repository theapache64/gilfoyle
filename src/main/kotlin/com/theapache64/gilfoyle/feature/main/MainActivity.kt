package com.theapache64.gilfoyle.feature.main


import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.TextColors
import com.github.ajalt.mordant.terminal.TextColors.green
import com.github.ajalt.mordant.terminal.TextColors.red
import com.theapache64.cyclone.core.Activity
import com.theapache64.cyclone.core.Intent
import com.theapache64.gilfoyle.di.components.DaggerUninstallComponent
import com.yg.kotlin.inquirer.components.promptList
import com.yg.kotlin.inquirer.core.KInquirer
import javax.inject.Inject

class MainActivity : Activity() {

    companion object {
        private const val KEY_DEVICE = "device"
        fun getStartIntent(device: String): Intent {
            return Intent(to = MainActivity::class).apply {
                putExtra(KEY_DEVICE, device)
            }
        }
    }

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        DaggerUninstallComponent.builder().build().inject(this)

        val device = getStringExtra(KEY_DEVICE)
        viewModel.init(device)
        val terminal = Terminal()

        // Watching for apps
        viewModel.apps.observe { apps ->
            if (apps.isNotEmpty()) {

                terminal.println(green("${apps.size} apps found"))

                var uninstallCount = 0
                var skipCount = 0
                for (app in apps) {
                    var action: String?
                    do {

                        action = KInquirer.promptList(
                            "Package name : ${green(app)}",
                            MainViewModel.actions
                        )

                        when (action) {

                            MainViewModel.ACTION_SKIP -> {
                                terminal.print(TextColors.blue("Skipped : '$app'"))
                                viewModel.onAnalyzed(app)
                                skipCount++
                            }
                            MainViewModel.ACTION_OPEN -> {
                                // open app
                                val isOpened = viewModel.onOpenAppSelected(app)
                                if (isOpened.not()) {
                                    // to try again
                                    action = null
                                }
                            }

                            MainViewModel.ACTION_UNINSTALL -> {
                                // uninstall app
                                val isUninstalled = viewModel.onUninstallSelected(app)
                                if (isUninstalled) {
                                    uninstallCount++
                                } else {
                                    // to try again
                                    action = null
                                }

                            }
                        }
                    } while (action == null || action == MainViewModel.ACTION_OPEN)

                    if (uninstallCount > 0) {
                        terminal.println(green("$uninstallCount app(s) uninstalled"))
                    }

                    if (skipCount > 0) {
                        terminal.println(green("$uninstallCount app(s) skipped"))
                    }
                }
            } else {
                // no apps
                terminal.println(red("No apps found in '$device'"))
            }
        }

        // Watching for open failed message
        viewModel.showOpenFailed.observe { app ->
            terminal.println(red("Failed to open '$app'. Try again"))
        }

        // Watching for uninstall failed
        viewModel.showUninstallFailed.observe { app ->
            terminal.println(red("Failed to uninstall '$app'. Try again"))
        }
    }
}