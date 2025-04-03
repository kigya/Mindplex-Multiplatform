package dev.kigya.mindplex.feature.profile.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextStyle
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.text.provides

internal object ProfileTheme : MindplexTheme() {

    // region ColorScheme
    val MindplexColorScheme.profileBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris10,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.profileNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.profileIcons
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris80,
            dark = super.color.gunmetal60,
        )

    val MindplexColorScheme.userNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.userStatisticsCardBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.userStatisticCategoryNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal60,
            dark = super.color.gunmetal60,
        )

    val MindplexColorScheme.userStatisticScoreText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.verticalDivider
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris10,
            dark = super.color.iris10,
        )

    val MindplexColorScheme.themeNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris80,
            dark = super.color.white,
        )

    val MindplexColorScheme.switchBorder
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris80,
            dark = super.color.white,
        )

    val MindplexColorScheme.switchTrack
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.transparent,
            dark = super.color.white,
        )

    val MindplexColorScheme.switchThumb
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris80,
            dark = super.color.iris80,
        )
    // endregion

    // region Typography
    val MindplexTypography.profileNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp20,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.userNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp24,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.userStatisticCategoryNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.userStatisticScoreText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.themeNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion
}
