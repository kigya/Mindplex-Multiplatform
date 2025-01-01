package dev.kigya.mindplex.feature.game.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextStyle
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.text.provides

internal object GameTheme : MindplexTheme() {

    // region ColorScheme
    val MindplexColorScheme.gameBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris10,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.gameIconBack
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris30,
        )

    val MindplexColorScheme.gameTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.gameScoreLabelBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.gameScoreLabelIcon
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris30,
        )

    val MindplexColorScheme.gameScoreLabelText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.gameTimerBackgroundArc
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris40,
            dark = super.color.iris60,
        )

    val MindplexColorScheme.gameTimerProgressArc
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.gameTimerText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.gameQuestion
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.gameInactiveOptionBorder
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )

    val MindplexColorScheme.gameInactiveOptionText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )

    val MindplexColorScheme.gameInactiveOptionBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.transparent,
            dark = super.color.transparent,
        )

    val MindplexColorScheme.gameWrongOptionBorder
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.mandy,
            dark = super.color.crayola,
        )

    val MindplexColorScheme.gameWrongOptionBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.transparent,
            dark = super.color.transparent,
        )

    val MindplexColorScheme.gameWrongOptionText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.mandy,
            dark = super.color.crayola,
        )

    val MindplexColorScheme.gameRightOptionBorder
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.apple,
            dark = super.color.apple,
        )

    val MindplexColorScheme.gameRightOptionBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.apple,
            dark = super.color.apple,
        )

    val MindplexColorScheme.gameRightOptionText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )
    // endregion ColorScheme

    // region Typography
    val MindplexTypography.gameTitle
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.gameScoreLabel
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.gameTimerCounter
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp18,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.gameQuestion
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp20,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.gameButton
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion Typography
}
