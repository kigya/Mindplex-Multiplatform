package dev.kigya.mindplex.core.presentation.component

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
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme

private const val RIPPLE_ALPHA = .5f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindplexChip(
    modifier: Modifier = Modifier,
    labelText: String,
    textColor: Color,
    textStyle: TextStyle,
    backgroundColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
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
            label = {
                MindplexText(
                    modifier = Modifier.padding(
                        vertical = MindplexTheme.dimension.dp8,
                        horizontal = MindplexTheme.dimension.dp4,
                    ),
                    text = labelText,
                    color = if (isSelected) textColor else backgroundColor,
                    style = textStyle,
                )
            },
            shape = MindplexTheme.shape.rounding16,
            colors = FilterChipDefaults.filterChipColors().copy(
                containerColor = textColor,
                disabledContainerColor = textColor,
                selectedContainerColor = backgroundColor,
            ),
            border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isSelected,
                borderColor = backgroundColor,
                selectedBorderColor = Color.Transparent,
                borderWidth = MindplexTheme.dimension.dp2,
                selectedBorderWidth = MindplexTheme.dimension.dp2,
            ),
            onClick = onClick,
        )
    }
}
