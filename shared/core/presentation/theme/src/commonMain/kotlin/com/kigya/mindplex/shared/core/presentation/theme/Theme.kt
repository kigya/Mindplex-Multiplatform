package com.kigya.mindplex.shared.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import com.kigya.mindplex.shared.core.presentation.theme.color.AmericanGreen
import com.kigya.mindplex.shared.core.presentation.theme.color.Crayola
import com.kigya.mindplex.shared.core.presentation.theme.color.Gunmetal100
import com.kigya.mindplex.shared.core.presentation.theme.color.Gunmetal60
import com.kigya.mindplex.shared.core.presentation.theme.color.Gunmetal80
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris10
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris100
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris20
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris30
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris40
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris50
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris60
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris70
import com.kigya.mindplex.shared.core.presentation.theme.color.Iris80

val LightColorScheme =
    lightColorScheme(
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

val DarkColorScheme =
    darkColorScheme(
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
        onSecondaryContainer = Iris70,
        tertiary = Iris50,
        tertiaryContainer = Iris60,
        error = Crayola,
        errorContainer = Iris80,
        scrim = AmericanGreen,
    )

@Composable
expect fun MindplexTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
)
