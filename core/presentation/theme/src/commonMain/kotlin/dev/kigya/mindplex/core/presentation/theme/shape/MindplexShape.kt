package dev.kigya.mindplex.core.presentation.theme.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.theme.dimension.MindplexDimension

@Immutable
data object MindplexShape {

    val rounding8: MindplexDsToken<RoundedCornerShape>
        @Composable get() = MindplexDsToken(RoundedCornerShape(MindplexDimension.dp8.value))

    val rounding16: MindplexDsToken<RoundedCornerShape>
        @Composable get() = MindplexDsToken(RoundedCornerShape(MindplexDimension.dp16.value))

    val rounding24: MindplexDsToken<RoundedCornerShape>
        @Composable get() = MindplexDsToken(RoundedCornerShape(MindplexDimension.dp24.value))
}
