package dev.kigya.mindplex.navigation.navigator.navigator

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import kotlin.system.exitProcess

@Composable
actual fun NavigationExitHandler(shouldExit: Boolean) {
    if (shouldExit) {
        LocalActivity.current?.finish()
        exitProcess(0)
    }
}
