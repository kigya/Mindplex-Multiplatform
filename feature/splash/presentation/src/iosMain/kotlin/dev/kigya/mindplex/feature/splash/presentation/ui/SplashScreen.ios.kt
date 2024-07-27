package dev.kigya.mindplex.feature.splash.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.common.util.getScreenWidth

@Composable
internal actual fun getLottieSplashSize(): Dp = with(LocalDensity.current) {
    (getScreenWidth() / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER).toDp()
}
