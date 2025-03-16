@file:Suppress("TopLevelPropertyNaming", "PropertyName", "SpreadOperator")

package dev.kigya.mindplex.core.presentation.theme

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColor
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.dimension.MindplexDimension
import dev.kigya.mindplex.core.presentation.theme.shape.MindplexShape
import dev.kigya.mindplex.core.presentation.theme.text.MindplexFont
import dev.kigya.mindplex.core.presentation.theme.text.MindplexFontWeight
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextSize
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindowHeight
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindowWidth
import dev.kigya.mindplex.core.presentation.theme.window.Window
import kotlin.jvm.JvmInline

@Composable
fun MindplexTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        values = arrayOf(
            LocalWindow provides Window,
            LocalWindowWidth provides currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass,
            LocalWindowHeight provides currentWindowAdaptiveInfo().windowSizeClass.windowHeightSizeClass,
            *platformCompositionValues,
        ),
        content = content,
    )
}

expect val platformCompositionValues: Array<ProvidedValue<*>>

@JvmInline
value class MindplexDsToken<T>(val value: T)

abstract class MindplexTheme {

    val font = MindplexFont
    val shape = MindplexShape
    val dimension = MindplexDimension
    val typography = MindplexTypography()
    val textSize = MindplexTextSize
    val colorScheme = MindplexColorScheme()

    protected val fontWeight = MindplexFontWeight
    protected val color = MindplexColor()
}
