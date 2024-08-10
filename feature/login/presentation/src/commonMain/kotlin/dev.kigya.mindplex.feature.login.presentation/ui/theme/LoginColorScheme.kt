package dev.kigya.mindplex.feature.login.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides

internal val MindplexColorScheme.loginBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris10,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.loginMindplexIcon
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris30,
    )

internal val MindplexColorScheme.loginTitle
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.loginSignInButtonContent
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.gunmetal100,
    )

internal val MindplexColorScheme.loginSignInButtonContainer
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )
