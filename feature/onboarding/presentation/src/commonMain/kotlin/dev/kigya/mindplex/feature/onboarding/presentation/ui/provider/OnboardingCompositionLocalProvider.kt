@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.feature.onboarding.presentation.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.common.util.requiredCompositionLocalOf
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme

private val smallDeviceHeight = 800.dp

internal object OnboardingAdaptiveMetrics : AdaptiveMetrics() {

    private val onboardingLottieWidthRatio
        @Composable
        get() = windowSizeClassWhen(
            compact = if (LocalWindow.current.height.dp < smallDeviceHeight) 2.5f else 2.1f,
            medium = 2.5f,
            expanded = 3f,
        )
    val LocalOnboardingLottieWidthRatio = requiredCompositionLocalOf<Float>()

    private val onboardingButtonsHorizontalPadding
        @Composable
        get() = windowSizeClassWhen(
            compact = OnboardingTheme.dimension.dp24.value,
            medium = OnboardingTheme.dimension.dp36.value,
            expanded = OnboardingTheme.dimension.dp64.value,
        )
    val LocalOnboardingButtonsHorizontalPadding = requiredCompositionLocalOf<Dp>()

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf(
        LocalOnboardingLottieWidthRatio provides onboardingLottieWidthRatio,
        LocalOnboardingButtonsHorizontalPadding provides onboardingButtonsHorizontalPadding,
    )
}

@Composable
internal fun OnboardingCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = OnboardingAdaptiveMetrics,
        content = content,
    )
