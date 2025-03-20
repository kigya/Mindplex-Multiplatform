package dev.kigya.mindplex.navigation.navigator.navigator

import androidx.compose.runtime.Composable
import platform.posix.exit

@Composable
actual fun NavigationExitHandler(shouldExit: Boolean) {
    if (shouldExit) exit(0)
}
