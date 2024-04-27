package dev.kigya.mindplex.core.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing

enum class MindplexSpacerOrientation { VERTICAL, HORIZONTAL }

enum class MindplexSpacerSize {
    DEFAULT, EXTRA_SMALL, SMALL, MEDIUM, LARGE, EXTRA_LARGE, GIANT;

    internal val value: Dp
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            DEFAULT -> MaterialTheme.spacing.default
            EXTRA_SMALL -> MaterialTheme.spacing.extraSmall
            SMALL -> MaterialTheme.spacing.small
            MEDIUM -> MaterialTheme.spacing.medium
            LARGE -> MaterialTheme.spacing.large
            EXTRA_LARGE -> MaterialTheme.spacing.extraLarge
            GIANT -> MaterialTheme.spacing.giant
        }
}

@Composable
fun MindplexSpacer(
    modifier: Modifier = Modifier,
    orientation: MindplexSpacerOrientation,
    size: MindplexSpacerSize = MindplexSpacerSize.DEFAULT,
) = drawSpacer(orientation, size, modifier)

@Composable
fun ColumnScope.MindplexSpacer(
    modifier: Modifier = Modifier,
    orientation: MindplexSpacerOrientation = MindplexSpacerOrientation.VERTICAL,
    size: MindplexSpacerSize = MindplexSpacerSize.DEFAULT,
) = drawSpacer(orientation, size, modifier)

@Composable
fun RowScope.MindplexSpacer(
    modifier: Modifier = Modifier,
    orientation: MindplexSpacerOrientation = MindplexSpacerOrientation.HORIZONTAL,
    size: MindplexSpacerSize = MindplexSpacerSize.DEFAULT,
) = drawSpacer(orientation, size, modifier)

@Composable
private fun drawSpacer(
    orientation: MindplexSpacerOrientation,
    size: MindplexSpacerSize,
    modifier: Modifier
) = if (orientation == MindplexSpacerOrientation.VERTICAL) {
    Spacer(modifier = Modifier.height(size.value).then(modifier))
} else {
    Spacer(modifier = Modifier.width(size.value).then(modifier))
}

