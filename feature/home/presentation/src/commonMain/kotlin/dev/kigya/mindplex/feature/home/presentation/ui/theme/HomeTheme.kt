package dev.kigya.mindplex.feature.home.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

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

    val MindplexColorScheme.homeFactsPagerBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris20,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.homeFactsPagerTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal80,
            dark = super.color.iris30,
        )

    val MindplexColorScheme.homeFactsPagerDescription
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
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

    val MindplexColorScheme.categorySelectionPopupBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris60,
        )

    val MindplexColorScheme.categorySelectionTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal80,
            dark = super.color.iris30,
        )

    val MindplexColorScheme.categorySelectionItem
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.categorySelectionDifficultyBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris60,
            dark = super.color.white,
        )

    val MindplexColorScheme.categorySelectionDifficultyText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris60,
        )

    val MindplexColorScheme.categorySelectionRipple
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )
    // endregion ColorScheme

    // region Typography
    val MindplexTypography.homeWelcomeBackText
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeProfileNameText
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp20,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeFactsPagerTitle
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeFactsPagerDescription
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.normal,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeModesCardTitle
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.homeModesCardDescription
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.normal,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.categorySelectionTitle
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.categorySelectionItem
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp14,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.categorySelectionButton
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion Typography
}
