package dev.kigya.mindplex.core.presentation.theme.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import dev.kigya.mindplex.core.presentation.theme.LocalTheme
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken

@Immutable
class MindplexColorScheme internal constructor()

@Immutable
data class MindplexDynamicColor(
    val light: MindplexDsToken<Color>,
    val dark: MindplexDsToken<Color>,
)

@Composable
infix fun MindplexColorScheme.provides(colors: MindplexDynamicColor): MindplexDsToken<Color> {
    val isDarkTheme = LocalTheme.current.isDark
    return if (isDarkTheme) colors.dark else colors.light
}
