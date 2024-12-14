package dev.kigya.mindplex.core.presentation.component.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

internal object ComponentTheme : MindplexTheme() {

    // region ColorScheme
    val MindplexColorScheme.componentTypewriterText
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.gunmetal100,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentPlaceholderShimmer
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris30,
            dark = super.color.iris40,
        )

    val MindplexColorScheme.componentJumpingDotsIndicator
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

    val MindplexColorScheme.componentErrorStubButtonContent
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentErrorStubButtonContainer
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.iris100,
        )

    val MindplexColorScheme.componentButtonContainer
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.componentButtonContent
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris100,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentButtonDisabledContainer
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris100,
            dark = super.color.white,
        )

    val MindplexColorScheme.componentButtonDisabledContent
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris80,
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
    val MindplexTypography.componentTypewriterText
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp20,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.componentErrorStubTitle
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.componentErrorStubButton
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion Typography
}
