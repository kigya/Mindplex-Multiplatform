package dev.kigya.mindplex.core.presentation.theme.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data object Window {
    val width: Int
        @Composable get() = getScreenWidth()
    val height: Int
        @Composable get() = getScreenHeight()
}

val LocalWindow = staticCompositionLocalOf { Window }

@Composable
expect fun getScreenWidth(): Int

@Composable
expect fun getScreenHeight(): Int
