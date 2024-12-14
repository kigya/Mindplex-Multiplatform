package dev.kigya.mindplex.core.presentation.theme.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.theme.dimension.MindplexDimension

@Immutable
data object MindplexShape {

    val rounding8: RoundedCornerShape
        @Composable get() = RoundedCornerShape(MindplexDimension.dp8)

    val rounding16: RoundedCornerShape
        @Composable get() = RoundedCornerShape(MindplexDimension.dp16)
}
