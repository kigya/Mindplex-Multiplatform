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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme.componentButtonContainer
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme.componentButtonContent
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme.componentButtonDisabledContainer
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme.componentButtonDisabledContent
import dev.kigya.mindplex.core.util.extension.empty

@Composable
fun MindplexButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    labelText: String = String.empty,
    contentPadding: PaddingValues = PaddingValues(ComponentTheme.dimension.dp16),
    contentSpace: Dp = ComponentTheme.dimension.dp16,
    containerColor: Color = ComponentTheme.colorScheme.componentButtonContainer,
    contentColor: Color = ComponentTheme.colorScheme.componentButtonContent,
    disabledContainerColor: Color = ComponentTheme.colorScheme.componentButtonDisabledContainer,
    disabledContentColor: Color = ComponentTheme.colorScheme.componentButtonDisabledContent,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Button(
        enabled = isEnabled,
        shape = CircleShape,
        contentPadding = PaddingValues(ComponentTheme.dimension.dp0),
        modifier = modifier
            .requiredHeightIn(min = ComponentTheme.dimension.dp64)
            .shadow(
                shape = CircleShape,
                elevation = ComponentTheme.dimension.dp0,
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
            modifier = Modifier.height(ComponentTheme.dimension.dp64),
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
                    MindplexText(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(
                                start = if (startIcon != null) contentSpace else ComponentTheme.dimension.dp0,
                                end = if (endIcon != null) contentSpace else ComponentTheme.dimension.dp0,
                            ),
                        text = labelText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = if (startIcon == null && endIcon == null) TextAlign.Center else TextAlign.Start,
                        style = textStyle,
                    )
                    endIcon?.invoke()
                }
            }
        }
    }
}
