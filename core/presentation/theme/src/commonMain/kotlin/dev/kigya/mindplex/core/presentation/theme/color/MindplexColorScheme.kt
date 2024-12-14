package dev.kigya.mindplex.core.presentation.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
class MindplexColorScheme internal constructor()

@Immutable
data class MindplexDynamicColor(
    val light: Color,
    val dark: Color,
)

@Composable
infix fun MindplexColorScheme.provides(colors: MindplexDynamicColor): Color =
    if (isSystemInDarkTheme()) colors.dark else colors.light
