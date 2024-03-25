package com.kigya.mindplex.shared.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.kigya.mindplex.shared.core.presentation.theme.spacing.LocalSpacing
import com.kigya.mindplex.shared.core.presentation.theme.spacing.Spacing
import com.kigya.mindplex.shared.core.presentation.theme.text.LocalTextSize
import com.kigya.mindplex.shared.core.presentation.theme.text.TextSize
import com.kigya.mindplex.shared.core.presentation.theme.text.Typography

@Composable
actual fun MindplexTheme(
    isDarkTheme: Boolean,
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
