package com.kigya.mindplex.shared.core.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kigya.mindplex.shared.core.presentation.theme.spacing.spacing
import com.kigya.mindplex.shared.core.presentation.theme.text.textSize

@Composable
internal fun MidplexButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    fontSize: TextUnit = MaterialTheme.textSize.medium,
    buttonLabel: String = "",
    isLoading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(MaterialTheme.spacing.medium),
    contentSpace: Dp = MaterialTheme.spacing.medium,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = White,
        disabledContainerColor = White,
        disabledContentColor = MaterialTheme.colorScheme.primaryContainer,
    ),
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    val density = LocalDensity.current

    Button(
        enabled = isEnabled,
        onClick = onClick,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .requiredHeightIn(min = 60.dp)
            .shadow(
                shape = CircleShape,
                elevation = 0.dp,
            ),
        colors = buttonColors,
    ) {
        var contentHeightDp by remember {
            mutableStateOf(60.dp)
        }

        AnimatedContent(
            modifier = Modifier.height(contentHeightDp),
            targetState = isLoading,
            label = "",
            transitionSpec = {
                fadeIn(tween(300)).togetherWith(fadeOut(tween(300)))
            }
        ) { showLoading ->
            if (showLoading) {
                Box(
                    modifier = Modifier.padding(contentPadding),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(24.dp),
                        color = White,
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .onSizeChanged {
                            with(density) {
                                contentHeightDp = it.height.toDp()
                            }
                        }
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    startIcon?.invoke()
                    Text(
                        text = buttonLabel,
                        fontSize = fontSize,
                        color = LocalContentColor.current,
                        letterSpacing = (-0.01).sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(
                                start = if (startIcon != null) contentSpace else 0.dp,
                                end = if (endIcon != null) contentSpace else 0.dp,
                            ),
                        textAlign = if (startIcon == null && endIcon == null) {
                            TextAlign.Center
                        } else {
                            TextAlign.Start
                        },
                    )
                    endIcon?.invoke()
                }
            }
        }
    }
}

