@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import dev.kigya.mindplex.core.presentation.theme.color.AmericanGreen
import dev.kigya.mindplex.core.presentation.theme.color.Crayola
import dev.kigya.mindplex.core.presentation.theme.color.Gunmetal100
import dev.kigya.mindplex.core.presentation.theme.color.Gunmetal60
import dev.kigya.mindplex.core.presentation.theme.color.Gunmetal80
import dev.kigya.mindplex.core.presentation.theme.color.Iris10
import dev.kigya.mindplex.core.presentation.theme.color.Iris100
import dev.kigya.mindplex.core.presentation.theme.color.Iris20
import dev.kigya.mindplex.core.presentation.theme.color.Iris30
import dev.kigya.mindplex.core.presentation.theme.color.Iris40
import dev.kigya.mindplex.core.presentation.theme.color.Iris50
import dev.kigya.mindplex.core.presentation.theme.color.Iris60
import dev.kigya.mindplex.core.presentation.theme.color.Iris70
import dev.kigya.mindplex.core.presentation.theme.color.Iris80
import dev.kigya.mindplex.core.presentation.theme.spacing.LocalSpacing
import dev.kigya.mindplex.core.presentation.theme.spacing.Spacing
import dev.kigya.mindplex.core.presentation.theme.text.LocalTextSize
import dev.kigya.mindplex.core.presentation.theme.text.TextSize
import dev.kigya.mindplex.core.presentation.theme.text.Typography
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.core.presentation.theme.window.Window

internal val LightColorScheme = lightColorScheme(
    background = Color.Iris10,
    onBackground = Color.Gunmetal100,
    surface = White,
    inverseSurface = Color.Iris70,
    onSurface = Color.Gunmetal100,
    surfaceVariant = White,
    onSurfaceVariant = Color.Gunmetal100,
    inverseOnSurface = Color.Gunmetal80,
    primary = Color.Iris10,
    onPrimary = Color.Gunmetal100,
    primaryContainer = Color.Iris70,
    onPrimaryContainer = White,
    secondary = Color.Iris80,
    onSecondary = White,
    secondaryContainer = White,
    onSecondaryContainer = Color.Iris70,
    tertiary = Color.Gunmetal60,
    tertiaryContainer = Color.Iris40,
    error = Color.Crayola,
    errorContainer = Color.Iris10,
    scrim = Color.AmericanGreen,
    surfaceContainer = Color.Gunmetal100,
    outline = Color.Iris20,
)

internal val DarkColorScheme = darkColorScheme(
    background = Color.Iris80,
    onBackground = White,
    surface = Color.Iris60,
    inverseSurface = Color.Iris30,
    onSurface = White,
    surfaceVariant = Color.Iris100,
    onSurfaceVariant = White,
    inverseOnSurface = Color.Iris30,
    primary = Color.Iris80,
    onPrimary = White,
    primaryContainer = White,
    onPrimaryContainer = Color.Iris70,
    secondary = Color.Iris80,
    onSecondary = White,
    secondaryContainer = White,
    onSecondaryContainer = Color.Iris80,
    tertiary = Color.Iris50,
    tertiaryContainer = Color.Iris60,
    error = Color.Crayola,
    errorContainer = Color.Iris80,
    scrim = Color.AmericanGreen,
    surfaceContainer = Color.Gunmetal100,
    outline = Color.Iris100,
)

@Composable
fun MindplexTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = remember(isDarkTheme) {
        if (isDarkTheme) DarkColorScheme else LightColorScheme
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalTextSize provides TextSize,
        LocalWindow provides Window,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
