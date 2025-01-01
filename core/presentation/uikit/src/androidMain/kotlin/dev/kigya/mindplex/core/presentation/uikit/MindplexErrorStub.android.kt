package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow

@Composable
internal actual fun getLottieErrorSize(): Dp =
    LocalWindow.current.width.dp / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER
