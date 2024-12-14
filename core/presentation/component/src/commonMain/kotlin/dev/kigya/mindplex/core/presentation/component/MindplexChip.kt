package dev.kigya.mindplex.core.presentation.component

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
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme

private const val RIPPLE_ALPHA = .3f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindplexChip(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    backgroundColor: Color,
    isSelected: Boolean,
    isEnabled: Boolean = true,
    shouldAnimateText: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    @Composable
    fun TextComponent(text: String) {
        MindplexText(
            modifier = Modifier.padding(
                vertical = ComponentTheme.dimension.dp8,
                horizontal = ComponentTheme.dimension.dp4,
            ),
            text = text,
            color = if (isSelected) textColor else backgroundColor,
            style = textStyle,
        )
    }

    val rippleConfiguration = remember {
        RippleConfiguration(
            color = textColor,
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
            shape = ComponentTheme.shape.rounding16,
            colors = FilterChipDefaults.filterChipColors().copy(
                containerColor = textColor,
                disabledContainerColor = textColor,
                selectedContainerColor = backgroundColor,
                disabledSelectedContainerColor = backgroundColor,
            ),
            border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isSelected,
                borderColor = backgroundColor,
                selectedBorderColor = Color.Transparent,
                borderWidth = ComponentTheme.dimension.dp2,
                selectedBorderWidth = ComponentTheme.dimension.dp2,
            ),
            leadingIcon = leadingIcon,
            onClick = onClick,
        )
    }
}
