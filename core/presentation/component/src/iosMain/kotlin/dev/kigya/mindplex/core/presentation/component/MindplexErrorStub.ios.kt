package dev.kigya.mindplex.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.common.util.getScreenWidth

@Composable
internal actual fun getLottieErrorSize(): Dp = with(LocalDensity.current) {
    (getScreenWidth() / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER).toDp()
}
