@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextDefaults.Typewriter.maxCharacterChunk
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextDefaults.Typewriter.maxDelayInMillis
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextDefaults.Typewriter.minCharacterChunk
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextDefaults.Typewriter.minDelayInMillis
import dev.kigya.mindplex.core.util.extension.empty
import kotlinx.coroutines.delay
import kotlin.random.Random

@Immutable
private object MindplexTextDefaults {

    @Immutable
    data object Typewriter {
        const val minDelayInMillis: Long = 10
        const val maxDelayInMillis: Long = 50
        const val minCharacterChunk: Int = 1
        const val maxCharacterChunk: Int = 5
    }
}

enum class MindplexTextAnimation {
    None,
    Typewriter,
    MovingText,
}

/**
 * [Figma](https://figmashort.link/p7MSJR)
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MindplexText(
    value: String,
    color: MindplexDsToken<Color>,
    typography: MindplexDsToken<TextStyle>,
    modifier: Modifier = Modifier,
    align: TextAlign = TextAlign.Center,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    animation: MindplexTextAnimation = MindplexTextAnimation.None,
    prefix: Boolean? = false,
) {
    when (animation) {
        MindplexTextAnimation.None ->
            Text(
                text = value,
                modifier = modifier,
                color = color.value,
                style = typography.value,
                maxLines = maxLines,
                minLines = minLines,
                textAlign = align,
                overflow = TextOverflow.Ellipsis,
            )

        MindplexTextAnimation.Typewriter -> {
            require(minDelayInMillis <= maxDelayInMillis)
            require(minCharacterChunk <= maxCharacterChunk)

            var displayedText by remember { mutableStateOf(String.empty) }

            Text(
                text = displayedText,
                modifier = modifier,
                color = color.value,
                style = typography.value,
                maxLines = maxLines,
                minLines = minLines,
                textAlign = align,
                overflow = TextOverflow.Ellipsis,
            )

            LaunchedEffect(value) {
                val textLength = value.length
                var endIndex = 0

                while (endIndex < textLength) {
                    endIndex = minOf(
                        a = endIndex + Random.nextInt(minCharacterChunk, maxCharacterChunk + 1),
                        b = textLength,
                    )
                    displayedText = value.substring(
                        startIndex = 0,
                        endIndex = endIndex,
                    )
                    delay(Random.nextLong(minDelayInMillis, maxDelayInMillis))
                }
            }
        }

        MindplexTextAnimation.MovingText -> {
            val initialValue = 0
            val targetValue = value.toIntOrNull() ?: 0

            var animatedValue by remember { mutableStateOf(initialValue) }

            LaunchedEffect(targetValue) {
                animate(
                    initialValue = initialValue.toFloat(),
                    targetValue = targetValue.toFloat(),
                    animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
                ) { value, _ ->
                    animatedValue = value.toInt()
                }
            }

            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                val text = if (prefix == true) "#$animatedValue" else "$animatedValue"

                Text(
                    text = text,
                    color = color.value,
                    style = typography.value,
                    maxLines = maxLines,
                    minLines = minLines,
                    textAlign = align,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
