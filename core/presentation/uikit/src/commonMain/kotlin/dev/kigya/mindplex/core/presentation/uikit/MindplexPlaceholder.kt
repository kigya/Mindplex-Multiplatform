package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import com.valentinilk.shimmer.shimmer
import dev.kigya.mindplex.core.presentation.common.extension.by
import dev.kigya.mindplex.core.presentation.common.util.DimensionSubcomposeLayout
import dev.kigya.mindplex.core.presentation.common.util.measureText
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentPlaceholderShimmer

@Immutable
private object MindplexPlaceholderDefaults {

    @Immutable
    data object Colors {

        val placeholder: MindplexDsToken<Color>
            @Composable
            get() = UiKitTheme.colorScheme.componentPlaceholderShimmer
    }
}

@Composable
fun MindplexMeasurablePlaceholder(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    color: MindplexDsToken<Color> = MindplexPlaceholderDefaults.Colors.placeholder,
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
                    .clip(UiKitTheme.shape.rounding24.value)
                    .shimmer()
                    .background(color.value),
                content = {},
            )
        },
        shouldPlaceMainContent = isLoading.not(),
    )
}

@ExperimentalMindplexUiKitApi
@Composable
fun MindplexMeasurablePlaceholder(
    isLoading: Boolean,
    textToMeasure: String,
    textStyle: MindplexDsToken<TextStyle>,
    modifier: Modifier = Modifier,
    color: MindplexDsToken<Color> = MindplexPlaceholderDefaults.Colors.placeholder,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val measuredText = measureText(text = textToMeasure, style = textStyle.value)

    if (isLoading) {
        Row(
            modifier = modifier
                .size(
                    width = measuredText.width by density,
                    height = measuredText.height by density,
                )
                .clip(UiKitTheme.shape.rounding24.value)
                .shimmer()
                .background(color.value),
            content = {},
        )
    } else {
        content()
    }
}

@ExperimentalMindplexUiKitApi
@Composable
fun MindplexPlaceholder(
    isLoading: Boolean,
    size: Size,
    modifier: Modifier = Modifier,
    color: MindplexDsToken<Color> = MindplexPlaceholderDefaults.Colors.placeholder,
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
                .clip(UiKitTheme.shape.rounding24.value)
                .shimmer()
                .background(color.value),
            content = {},
        )
    } else {
        content()
    }
}

@ExperimentalMindplexUiKitApi
@Composable
fun MindplexPlaceholder(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    color: MindplexDsToken<Color> = MindplexPlaceholderDefaults.Colors.placeholder,
    content: @Composable () -> Unit,
) {
    if (isLoading) {
        Row(
            modifier = modifier
                .clip(UiKitTheme.shape.rounding24.value)
                .shimmer()
                .background(color.value),
            content = {},
        )
    } else {
        content()
    }
}
