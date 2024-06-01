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
import androidx.compose.material3.MaterialTheme
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
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing
import dev.kigya.mindplex.core.presentation.theme.text.textSize
import dev.kigya.mindplex.core.util.extension.empty

private const val TRANSITION_ANIMATION_DURATION = 300

@Composable
@Suppress("CognitiveComplexMethod")
fun MindplexButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    fontSize: TextUnit = MaterialTheme.textSize.medium,
    labelText: String = String.empty,
    contentPadding: PaddingValues = PaddingValues(MaterialTheme.spacing.medium),
    contentSpace: Dp = MaterialTheme.spacing.medium,
    containerColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContainerColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Button(
        enabled = isEnabled,
        shape = CircleShape,
        contentPadding = PaddingValues(MaterialTheme.spacing.none),
        modifier = modifier
            .requiredHeightIn(min = MaterialTheme.spacing.giant)
            .shadow(
                shape = CircleShape,
                elevation = MaterialTheme.spacing.none,
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
            modifier = Modifier.height(MaterialTheme.spacing.giant),
            targetState = isLoading,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = TRANSITION_ANIMATION_DURATION)) togetherWith
                    fadeOut(animationSpec = tween(durationMillis = TRANSITION_ANIMATION_DURATION))
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
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(
                                start = if (startIcon != null) contentSpace else MaterialTheme.spacing.none,
                                end = if (endIcon != null) contentSpace else MaterialTheme.spacing.none,
                            ),
                        textAlign = if (startIcon == null && endIcon == null) TextAlign.Center else TextAlign.Start,
                    )
                    endIcon?.invoke()
                }
            }
        }
    }
}
