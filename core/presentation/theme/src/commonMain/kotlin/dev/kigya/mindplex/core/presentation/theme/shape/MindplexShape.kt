package dev.kigya.mindplex.core.presentation.theme.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme

@Immutable
data object MindplexShape {
    val rounding16: RoundedCornerShape
        @Composable get() = RoundedCornerShape(MindplexTheme.dimension.dp16)
}

internal val LocalShape = staticCompositionLocalOf { MindplexShape }
