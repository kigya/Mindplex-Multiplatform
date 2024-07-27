package dev.kigya.mindplex.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.common.util.getScreenWidth

@Composable
internal actual fun getLottieErrorSize(): Dp =
    getScreenWidth().dp / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER
