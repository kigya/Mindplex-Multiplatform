package dev.kigya.mindplex.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.valentinilk.shimmer.shimmer
import dev.kigya.mindplex.core.presentation.common.extension.by
import dev.kigya.mindplex.core.presentation.common.util.DimensionSubcomposeLayout
import dev.kigya.mindplex.core.presentation.common.util.measureText
import dev.kigya.mindplex.core.presentation.component.theme.componentPlaceholderShimmer
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme

@Composable
fun MindplexPlaceholder(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    color: Color = MindplexTheme.colorScheme.componentPlaceholderShimmer,
    cornerRadius: Dp = MindplexTheme.dimension.dp24,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current

    DimensionSubcomposeLayout(
        mainContent = { content() },
        dependentContent = { size ->
            Row(
                modifier = modifier
                    .size(
                        width = size.width by density,
                        height = size.height by density,
                    )
                    .clip(RoundedCornerShape(cornerRadius))
                    .shimmer()
                    .background(color),
            ) { }
        },
        shouldPlaceMainContent = isLoading.not(),
    )
}

@Composable
fun MindplexPlaceholder(
    modifier: Modifier = Modifier,
    textToMeasure: String,
    textStyle: TextStyle,
    isLoading: Boolean = false,
    color: Color = MindplexTheme.colorScheme.componentPlaceholderShimmer,
    cornerRadius: Dp = MindplexTheme.dimension.dp24,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val measuredText = measureText(text = textToMeasure, style = textStyle)

    if (isLoading) {
        Row(
            modifier = modifier
                .size(
                    width = measuredText.width by density,
                    height = measuredText.height by density,
                )
                .clip(RoundedCornerShape(cornerRadius))
                .shimmer()
                .background(color),
        ) { }
    } else {
        content()
    }
}

@Composable
fun MindplexPlaceholder(
    modifier: Modifier = Modifier,
    size: Size = Size.Unspecified,
    isLoading: Boolean = false,
    color: Color = MindplexTheme.colorScheme.componentPlaceholderShimmer,
    cornerRadius: Dp = MindplexTheme.dimension.dp24,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current

    if (isLoading) {
        Row(
            modifier = modifier
                .size(
                    width = size.width by density,
                    height = size.height by density,
                )
                .clip(RoundedCornerShape(cornerRadius))
                .shimmer()
                .background(color),
        ) { }
    } else {
        content()
    }
}
