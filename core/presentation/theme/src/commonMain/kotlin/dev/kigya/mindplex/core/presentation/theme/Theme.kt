@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
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
import dev.kigya.mindplex.core.presentation.theme.color.White
import dev.kigya.mindplex.core.presentation.theme.spacing.LocalSpacing
import dev.kigya.mindplex.core.presentation.theme.spacing.Spacing
import dev.kigya.mindplex.core.presentation.theme.text.LocalTextSize
import dev.kigya.mindplex.core.presentation.theme.text.TextSize
import dev.kigya.mindplex.core.presentation.theme.text.Typography

internal val LightColorScheme = lightColorScheme(
    background = Iris10,
    onBackground = Gunmetal100,
    surface = White,
    inverseSurface = Gunmetal80,
    onSurface = Gunmetal100,
    surfaceVariant = Iris20,
    onSurfaceVariant = Gunmetal100,
    inverseOnSurface = Gunmetal80,
    primary = Iris10,
    onPrimary = Gunmetal100,
    primaryContainer = Iris70,
    onPrimaryContainer = White,
    secondary = Iris80,
    onSecondary = White,
    secondaryContainer = White,
    onSecondaryContainer = Iris70,
    tertiary = Gunmetal60,
    tertiaryContainer = Iris40,
    error = Crayola,
    errorContainer = Iris10,
    scrim = AmericanGreen,
)

internal val DarkColorScheme = darkColorScheme(
    background = Iris80,
    onBackground = White,
    surface = Iris60,
    inverseSurface = Iris30,
    onSurface = White,
    surfaceVariant = Iris100,
    onSurfaceVariant = White,
    inverseOnSurface = Iris30,
    primary = Iris80,
    onPrimary = White,
    primaryContainer = White,
    onPrimaryContainer = Iris70,
    secondary = Iris80,
    onSecondary = White,
    secondaryContainer = White,
    onSecondaryContainer = Iris80,
    tertiary = Iris50,
    tertiaryContainer = Iris60,
    error = Crayola,
    errorContainer = Iris80,
    scrim = AmericanGreen,
)

@Composable
fun MindplexTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = remember(isDarkTheme) {
        if (isDarkTheme) DarkColorScheme else LightColorScheme
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalTextSize provides TextSize,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
