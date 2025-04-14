package dev.kigya.mindplex.feature.home.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextStyle
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.text.provides

internal object HomeTheme : MindplexTheme() {

    // region ColorScheme
    val MindplexColorScheme.homeBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris10,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.homeWelcomeBackText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal80,
            dark = super.color.iris30,
        )

    val MindplexColorScheme.homeProfileNameText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeFactsPagerBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris20,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.homeFactsPagerTextShadow
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris70,
        )

    val MindplexColorScheme.homeFactsPagerTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeFactsPagerDescription
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeFactsPagerDotsIndicator
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeModesCardBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris60,
        )

    val MindplexColorScheme.homeModesIconBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris10,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.homeModesCardTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeModesCardDescription
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal80,
            dark = super.color.iris30,
        )

    val MindplexColorScheme.homeModesCardArrow
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeModesDelimiter
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris10,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.homeCategorySelectionPopupBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris60,
        )

    val MindplexColorScheme.homeCategorySelectionTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal80,
            dark = super.color.iris30,
        )

    val MindplexColorScheme.homeCategorySelectionItem
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeCategorySelectionDifficultyBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris60,
            dark = super.color.white,
        )

    val MindplexColorScheme.homeCategorySelectionDifficultyText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris60,
        )

    val MindplexColorScheme.homeCategorySelectionRipple
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )
    // endregion ColorScheme

    // region Typography
    val MindplexTypography.homeWelcomeBackText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeProfileNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp20,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeFactsPagerTitle
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp18,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeFactsPagerDescription
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.normal,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeModesCardTitle
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeModesCardDescription
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.normal,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeCategorySelectionTitle
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeCategorySelectionItem
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp14,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeCategorySelectionButton
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion Typography
}
