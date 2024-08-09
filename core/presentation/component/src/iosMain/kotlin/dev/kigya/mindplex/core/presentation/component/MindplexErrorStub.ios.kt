package dev.kigya.mindplex.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow

@Composable
internal actual fun getLottieErrorSize(): Dp = with(LocalDensity.current) {
    (LocalWindow.current.width / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER).toDp()
}
