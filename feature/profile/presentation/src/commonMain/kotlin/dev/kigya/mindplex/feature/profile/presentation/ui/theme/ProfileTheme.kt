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

    val MindplexColorScheme.profileUserNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.profileUserStatisticsCardBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.profileUserStatisticCategoryNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal60,
            dark = super.color.gunmetal60,
        )

    val MindplexColorScheme.profileUserStatisticScoreText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.profileVerticalDivider
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris20,
            dark = super.color.white20,
        )

    val MindplexColorScheme.profileThemeNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris80,
            dark = super.color.white,
        )

    val MindplexColorScheme.profileSwitchBorder
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris80,
            dark = super.color.white,
        )

    val MindplexColorScheme.profileSwitchTrack
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.transparent,
            dark = super.color.white,
        )

    val MindplexColorScheme.profileSwitchThumb
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

    val MindplexTypography.profileUserNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp24,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.profileUserStatisticCategoryNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.profileUserStatisticScoreText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.profileThemeNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion
}
