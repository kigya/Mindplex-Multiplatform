package dev.kigya.mindplex.feature.onboarding.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides

internal val MindplexColorScheme.onboardingBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.onboardingTitle
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.onboardingDescription
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.onboardingSkipButtonContent
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.onboardingNextButtonContainer
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.onboardingNextButtonContent
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris80,
    )
