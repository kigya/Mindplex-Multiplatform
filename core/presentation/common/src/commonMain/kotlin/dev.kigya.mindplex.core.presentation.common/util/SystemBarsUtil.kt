package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Composable

enum class SystemBarsColor {
    LIGHT,
    DARK,
    AUTO,
}

@Composable
expect fun SystemBarsColor(color: SystemBarsColor)
