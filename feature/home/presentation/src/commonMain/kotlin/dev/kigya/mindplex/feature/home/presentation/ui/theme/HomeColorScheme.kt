package dev.kigya.mindplex.feature.home.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides

internal val MindplexColorScheme.homeBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris10,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.homeWelcomeBackText
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal80,
        dark = MindplexTheme.color.iris30,
    )

internal val MindplexColorScheme.homeFactsPagerBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris20,
        dark = MindplexTheme.color.iris100,
    )

internal val MindplexColorScheme.homeFactsPagerTitle
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal80,
        dark = MindplexTheme.color.iris30,
    )

internal val MindplexColorScheme.homeFactsPagerDescription
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.homeFactsPagerDotsIndicator
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.homeModesCardBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.iris60,
    )

internal val MindplexColorScheme.homeModesIconBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris10,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.homeModesCardTitle
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.homeModesCardDescription
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal80,
        dark = MindplexTheme.color.iris30,
    )

internal val MindplexColorScheme.homeModesCardArrow
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.homeModesDelimiter
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris10,
        dark = MindplexTheme.color.iris80,
    )
