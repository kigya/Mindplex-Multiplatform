package dev.kigya.mindplex.feature.splash.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.common.util.getScreenWidth

@Composable
internal actual fun getLottieSplashSize(): Dp =
    getScreenWidth().dp / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER
