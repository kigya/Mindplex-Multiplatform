package dev.kigya.mindplex.core.presentation.component.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides

internal val MindplexColorScheme.componentTypewriterText
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentPlaceholderShimmer
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris30,
        dark = MindplexTheme.color.iris40,
    )

internal val MindplexColorScheme.componentJumpingDotsIndicator
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentErrorStubTitle
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentErrorStubButtonContent
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentErrorStubButtonContainer
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.iris100,
    )

internal val MindplexColorScheme.componentButtonContainer
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.componentButtonContent
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentButtonDisabledContainer
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentButtonDisabledContent
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.componentNavigationBarBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentNavigationBarBall
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.componentCircleIndicator
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.white,
    )
