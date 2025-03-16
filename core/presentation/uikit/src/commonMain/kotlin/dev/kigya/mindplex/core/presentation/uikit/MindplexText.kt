@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
}

/**
 * [Figma](https://figmashort.link/p7MSJR)
 */
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

            MindplexText(
                value = displayedText,
                color = color,
                typography = typography,
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
    }
}
