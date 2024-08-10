package dev.kigya.mindplex.feature.splash.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides

internal val MindplexColorScheme.splashBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.splashTitle
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )
