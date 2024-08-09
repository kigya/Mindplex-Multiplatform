package dev.kigya.mindplex.feature.splash.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow

@Composable
internal actual fun getLottieSplashSize(): Dp =
    LocalWindow.current.width.dp / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER
