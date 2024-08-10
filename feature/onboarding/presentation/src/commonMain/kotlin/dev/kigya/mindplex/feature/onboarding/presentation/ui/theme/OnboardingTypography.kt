package dev.kigya.mindplex.feature.onboarding.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

internal val MindplexTypography.onboardingTitle
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp24,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.onboardingDescription
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp16,
        fontWeight = FontWeight.Normal,
        fontFamily = MindplexTheme.font.rubik,
    )
