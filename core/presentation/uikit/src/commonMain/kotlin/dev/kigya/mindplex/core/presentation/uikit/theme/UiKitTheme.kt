package dev.kigya.mindplex.core.presentation.uikit.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextStyle
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.text.provides

internal object UiKitTheme : MindplexTheme() {

    // region ColorScheme
    val MindplexColorScheme.componentPlaceholderShimmer
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris30,
            dark = super.color.iris40,
        )

    val MindplexColorScheme.componentDotIndicator
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentErrorStubTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentErrorStubButtonText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentErrorStubButtonBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.componentButtonBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.componentButtonText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris100,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentNavigationBarBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentNavigationBarBall
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentCircleIndicator
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )
    // endregion ColorScheme

    // region Typography
    val MindplexTypography.componentButtonText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.componentTypewriterText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp20,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.componentErrorStubTitle
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.componentErrorStubButton
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion Typography
}
