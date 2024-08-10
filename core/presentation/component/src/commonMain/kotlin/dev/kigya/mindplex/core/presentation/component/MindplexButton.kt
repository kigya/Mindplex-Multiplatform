package dev.kigya.mindplex.core.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import dev.kigya.mindplex.core.presentation.component.theme.componentButtonContainer
import dev.kigya.mindplex.core.presentation.component.theme.componentButtonContent
import dev.kigya.mindplex.core.presentation.component.theme.componentButtonDisabledContainer
import dev.kigya.mindplex.core.presentation.component.theme.componentButtonDisabledContent
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.util.extension.empty

@Composable
fun MindplexButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    fontSize: TextUnit = MindplexTheme.textSize.sp16,
    labelText: String = String.empty,
    contentPadding: PaddingValues = PaddingValues(MindplexTheme.dimension.dp16),
    contentSpace: Dp = MindplexTheme.dimension.dp16,
    containerColor: Color = MindplexTheme.colorScheme.componentButtonContainer,
    contentColor: Color = MindplexTheme.colorScheme.componentButtonContent,
    disabledContainerColor: Color = MindplexTheme.colorScheme.componentButtonDisabledContainer,
    disabledContentColor: Color = MindplexTheme.colorScheme.componentButtonDisabledContent,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Button(
        enabled = isEnabled,
        shape = CircleShape,
        contentPadding = PaddingValues(MindplexTheme.dimension.dp0),
        modifier = modifier
            .requiredHeightIn(min = MindplexTheme.dimension.dp64)
            .shadow(
                shape = CircleShape,
                elevation = MindplexTheme.dimension.dp0,
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        ),
        onClick = onClick,
    ) {
        AnimatedContent(
            modifier = Modifier.height(MindplexTheme.dimension.dp64),
            targetState = isLoading,
            transitionSpec = {
                fadeIn(animationSpec = tween()) togetherWith
                    fadeOut(animationSpec = tween())
            },
        ) { showLoading ->
            if (showLoading.not()) {
                Row(
                    modifier = Modifier.padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    startIcon?.invoke()
                    Text(
                        text = labelText,
                        fontSize = fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(
                                start = if (startIcon != null) contentSpace else MindplexTheme.dimension.dp0,
                                end = if (endIcon != null) contentSpace else MindplexTheme.dimension.dp0,
                            ),
                        textAlign = if (startIcon == null && endIcon == null) TextAlign.Center else TextAlign.Start,
                    )
                    endIcon?.invoke()
                }
            }
        }
    }
}
