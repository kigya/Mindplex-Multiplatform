package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme

private const val RIPPLE_ALPHA = .3f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindplexChip(
    text: String,
    textColor: MindplexDsToken<Color>,
    textStyle: MindplexDsToken<TextStyle>,
    backgroundColor: MindplexDsToken<Color>,
    isSelected: Boolean,
    isEnabled: Boolean = true,
    shouldAnimateText: Boolean = false,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    @Composable
    fun TextComponent(text: String) {
        MindplexText(
            modifier = Modifier.padding(
                vertical = UiKitTheme.dimension.dp8.value,
                horizontal = UiKitTheme.dimension.dp4.value,
            ),
            value = text,
            color = if (isSelected) textColor else backgroundColor,
            typography = textStyle,
        )
    }

    val rippleConfiguration = remember {
        RippleConfiguration(
            color = textColor.value,
            rippleAlpha = RippleAlpha(
                draggedAlpha = RIPPLE_ALPHA,
                focusedAlpha = RIPPLE_ALPHA,
                hoveredAlpha = RIPPLE_ALPHA,
                pressedAlpha = RIPPLE_ALPHA,
            ),
        )
    }

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        FilterChip(
            modifier = modifier,
            selected = isSelected,
            enabled = isEnabled,
            label = {
                if (shouldAnimateText) {
                    AnimatedContent(
                        targetState = text,
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInVertically { -it } togetherWith slideOutVertically { it }
                            } else {
                                slideInVertically { it } togetherWith slideOutVertically { -it }
                            }
                        },
                    ) { count ->
                        TextComponent(count)
                    }
                } else {
                    TextComponent(text)
                }
            },
            shape = UiKitTheme.shape.rounding16.value,
            colors = FilterChipDefaults.filterChipColors().copy(
                containerColor = textColor.value,
                disabledContainerColor = textColor.value,
                selectedContainerColor = backgroundColor.value,
                disabledSelectedContainerColor = backgroundColor.value,
            ),
            border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isSelected,
                borderColor = backgroundColor.value,
                selectedBorderColor = Color.Transparent,
                borderWidth = UiKitTheme.dimension.dp2.value,
                selectedBorderWidth = UiKitTheme.dimension.dp2.value,
            ),
            leadingIcon = leadingIcon,
            onClick = onClick,
        )
    }
}
