@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentButtonText

@Immutable
private object MindplexButtonDefaults {

    @Immutable
    data object Padding {

        val vertical: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp16

        val horizontal: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp16

        val materialContentPadding: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp0

        val textPaddingWithIcon: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp16

        val textPaddingNoIcon: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp0
    }

    @Immutable
    data object Typography {

        val standard: MindplexDsToken<TextStyle>
            @Composable
            get() = UiKitTheme.typography.componentButtonText
    }

    @Immutable
    data object Size {

        val requiredHeight: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp64

        val borderWidth: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp2

        val elevation: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp0
    }

    @Immutable
    data object Rules {

        const val maxLines: Int = 1
    }

    @Immutable
    data object State {

        const val isLoading: Boolean = false
    }
}

/**
 * [Figma](https://figmashort.link/t5t4nn)
 */
@Composable
fun MindplexButton(
    text: String,
    backgroundColor: MindplexDsToken<Color>,
    borderColor: MindplexDsToken<Color>,
    textColor: MindplexDsToken<Color>,
    modifier: Modifier = Modifier,
    textTypography: MindplexDsToken<TextStyle> = MindplexButtonDefaults.Typography.standard,
    verticalPadding: MindplexDsToken<Dp> = MindplexButtonDefaults.Padding.vertical,
    horizontalPadding: MindplexDsToken<Dp> = MindplexButtonDefaults.Padding.horizontal,
    leadingIcon: (@Composable () -> Unit)? = null,
    isLoading: Boolean = MindplexButtonDefaults.State.isLoading,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .requiredHeightIn(min = MindplexButtonDefaults.Size.requiredHeight.value)
            .shadow(
                shape = CircleShape,
                elevation = MindplexButtonDefaults.Size.elevation.value,
            ),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = backgroundColor.value,
            contentColor = textColor.value,
        ),
        border = BorderStroke(
            width = MindplexButtonDefaults.Size.borderWidth.value,
            color = borderColor.value,
        ),
        contentPadding = PaddingValues(MindplexButtonDefaults.Padding.materialContentPadding.value),
    ) {
        AnimatedContent(
            targetState = isLoading,
            modifier = Modifier.height(MindplexButtonDefaults.Size.requiredHeight.value),
            transitionSpec = {
                fadeIn(animationSpec = tween()) togetherWith
                    fadeOut(animationSpec = tween())
            },
        ) { showLoading ->
            if (showLoading.not()) {
                Row(
                    modifier = Modifier.padding(
                        PaddingValues(
                            vertical = verticalPadding.value,
                            horizontal = horizontalPadding.value,
                        ),
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    leadingIcon?.invoke()

                    MindplexText(
                        value = text,
                        color = textColor,
                        typography = textTypography,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(
                                start = if (leadingIcon != null) {
                                    MindplexButtonDefaults.Padding.textPaddingWithIcon.value
                                } else {
                                    MindplexButtonDefaults.Padding.textPaddingNoIcon.value
                                },
                                end = MindplexButtonDefaults.Padding.textPaddingNoIcon.value,
                            ),
                        align = if (leadingIcon == null) TextAlign.Center else TextAlign.Start,
                        maxLines = MindplexButtonDefaults.Rules.maxLines,
                    )
                }
            }
        }
    }
}
