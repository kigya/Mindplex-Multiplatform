package dev.kigya.mindplex.core.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme

enum class MindplexSpacerOrientation {
    VERTICAL,
    HORIZONTAL,
}

@Composable
fun MindplexSpacer(
    modifier: Modifier = Modifier,
    orientation: MindplexSpacerOrientation,
    size: Dp = MindplexTheme.dimension.dp0,
) = drawSpacer(modifier, orientation, size)

@Composable
fun ColumnScope.MindplexSpacer(
    modifier: Modifier = Modifier,
    orientation: MindplexSpacerOrientation = MindplexSpacerOrientation.VERTICAL,
    size: Dp = MindplexTheme.dimension.dp0,
) = drawSpacer(modifier, orientation, size)

@Composable
fun RowScope.MindplexSpacer(
    modifier: Modifier = Modifier,
    orientation: MindplexSpacerOrientation = MindplexSpacerOrientation.HORIZONTAL,
    size: Dp = MindplexTheme.dimension.dp0,
) = drawSpacer(modifier, orientation, size)

@Composable
private fun drawSpacer(
    modifier: Modifier,
    orientation: MindplexSpacerOrientation,
    size: Dp,
) = if (orientation == MindplexSpacerOrientation.VERTICAL) {
    Spacer(modifier = Modifier.height(size).then(modifier))
} else {
    Spacer(modifier = Modifier.width(size).then(modifier))
}
