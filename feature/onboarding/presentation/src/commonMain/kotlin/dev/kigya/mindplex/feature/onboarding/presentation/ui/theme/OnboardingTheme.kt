package dev.kigya.mindplex.feature.onboarding.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

internal object OnboardingTheme : MindplexTheme() {

    // region ColorScheme
    val MindplexColorScheme.onboardingBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.onboardingTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.onboardingDescription
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.onboardingSkipButtonContent
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.onboardingNextButtonContainer
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )

    val MindplexColorScheme.onboardingNextButtonContent
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris80,
        )
    // endregion ColorScheme

    // region Typography
    val MindplexTypography.onboardingTitle
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp24,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.onboardingDescription
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.normal,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.onboardingButton
        @Composable
        get() = TextStyle(
            fontSize = super.textSize.sp16,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
    // endregion Typography
}
