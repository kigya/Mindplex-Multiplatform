package dev.kigya.mindplex.feature.game.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides

internal val MindplexColorScheme.gameBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris10,
        dark = MindplexTheme.color.iris80,
    )

internal val MindplexColorScheme.gameIconBack
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris30,
    )

internal val MindplexColorScheme.gameTitle
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.gameScoreLabelBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.iris100,
    )

internal val MindplexColorScheme.gameScoreLabelIcon
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris30,
    )

internal val MindplexColorScheme.gameScoreLabelText
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.gameTimerBackgroundArc
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris40,
        dark = MindplexTheme.color.iris60,
    )

internal val MindplexColorScheme.gameTimerProgressArc
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.iris100,
    )

internal val MindplexColorScheme.gameTimerText
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.gameQuestion
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.gunmetal100,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.gameInactiveOptionBorder
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.gameInactiveOptionText
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.iris70,
        dark = MindplexTheme.color.white,
    )

internal val MindplexColorScheme.gameInactiveOptionBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.transparent,
        dark = MindplexTheme.color.transparent,
    )

internal val MindplexColorScheme.gameWrongOptionBorder
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.lightCarminePink,
        dark = MindplexTheme.color.crayola,
    )

internal val MindplexColorScheme.gameWrongOptionBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.transparent,
        dark = MindplexTheme.color.transparent,
    )

internal val MindplexColorScheme.gameWrongOptionText
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.lightCarminePink,
        dark = MindplexTheme.color.crayola,
    )

internal val MindplexColorScheme.gameRightOptionBorder
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.americanGreen,
        dark = MindplexTheme.color.americanGreen,
    )

internal val MindplexColorScheme.gameRightOptionBackground
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.americanGreen,
        dark = MindplexTheme.color.americanGreen,
    )

internal val MindplexColorScheme.gameRightOptionText
    @Composable
    get() = this provides MindplexDynamicColor(
        light = MindplexTheme.color.white,
        dark = MindplexTheme.color.white,
    )
