@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.feature.splash.presentation.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.common.util.requiredCompositionLocalOf
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider

internal object SplashAdaptiveMetrics : AdaptiveMetrics() {

    private val splashLogoWidthRatio
        @Composable
        get() = windowSizeClassWhen(
            compact = 3f,
            medium = 5f,
            expanded = 11f,
        )
    val LocalSplashLogoWidthRatio = requiredCompositionLocalOf<Float>()

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf(
        LocalSplashLogoWidthRatio provides splashLogoWidthRatio,
    )
}

@Composable
internal fun SplashCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = SplashAdaptiveMetrics,
        content = content,
    )
