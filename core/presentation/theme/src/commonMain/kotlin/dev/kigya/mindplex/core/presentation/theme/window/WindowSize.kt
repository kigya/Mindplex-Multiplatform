package dev.kigya.mindplex.core.presentation.theme.window

import androidx.compose.runtime.compositionLocalOf
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

val LocalWindowWidth = compositionLocalOf { WindowWidthSizeClass.COMPACT }
val LocalWindowHeight = compositionLocalOf { WindowHeightSizeClass.COMPACT }
