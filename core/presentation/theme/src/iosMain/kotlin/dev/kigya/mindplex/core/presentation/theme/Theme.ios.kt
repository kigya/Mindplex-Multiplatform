package dev.kigya.mindplex.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import dev.kigya.mindplex.core.presentation.theme.spacing.LocalSpacing
import dev.kigya.mindplex.core.presentation.theme.spacing.Spacing
import dev.kigya.mindplex.core.presentation.theme.text.LocalTextSize
import dev.kigya.mindplex.core.presentation.theme.text.TextSize
import dev.kigya.mindplex.core.presentation.theme.text.Typography

@Composable
actual fun MindplexTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalTextSize provides TextSize,
    ) {
        val colorScheme = remember(isDarkTheme) {
            if (isDarkTheme) DarkColorScheme else LightColorScheme
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
