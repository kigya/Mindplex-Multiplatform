@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.feature.profile.presentation.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.common.util.requiredCompositionLocalOf
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider

internal object ProfileAdaptiveMetrics : AdaptiveMetrics() {

    private val ProfileIconsWidthRatio
        @Composable
        get() = windowSizeClassWhen(
            compact = 1f,
            medium = 2f,
            expanded = 2.3f,
        )
    val LocalProfiledWidthRatio = requiredCompositionLocalOf<Float>()

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf(
        LocalProfiledWidthRatio provides ProfileIconsWidthRatio,
    )
}

@Composable
internal fun ProfileCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = ProfileAdaptiveMetrics,
        content = content,
    )
