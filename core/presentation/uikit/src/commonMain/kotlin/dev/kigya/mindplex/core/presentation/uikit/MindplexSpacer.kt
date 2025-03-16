package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme

enum class MindplexSpacerOrientation {
    VERTICAL,
    HORIZONTAL,
}

/**
 * [Figma](https://figmashort.link/PSMpMW)
 */
@Composable
fun ColumnScope.MindplexSpacer(
    modifier: Modifier = Modifier,
    size: MindplexDsToken<Dp> = UiKitTheme.dimension.dp0,
) = drawOrientedSpacer(
    modifier = modifier,
    orientation = MindplexSpacerOrientation.VERTICAL,
    size = size,
)

/**
 * [Figma](https://figmashort.link/PSMpMW)
 */
@Composable
fun RowScope.MindplexSpacer(
    modifier: Modifier = Modifier,
    size: MindplexDsToken<Dp> = UiKitTheme.dimension.dp0,
) = drawOrientedSpacer(
    modifier = modifier,
    orientation = MindplexSpacerOrientation.HORIZONTAL,
    size = size,
)

@Composable
private fun drawOrientedSpacer(
    modifier: Modifier,
    orientation: MindplexSpacerOrientation,
    size: MindplexDsToken<Dp>,
) = if (orientation == MindplexSpacerOrientation.VERTICAL) {
    Spacer(modifier = Modifier.height(size.value).then(modifier))
} else {
    Spacer(modifier = Modifier.width(size.value).then(modifier))
}
