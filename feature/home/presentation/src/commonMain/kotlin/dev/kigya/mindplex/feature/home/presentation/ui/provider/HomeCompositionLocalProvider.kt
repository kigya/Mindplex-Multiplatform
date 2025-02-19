@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.feature.home.presentation.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.layout.ContentScale
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.common.util.requiredCompositionLocalOf
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider

internal object HomeAdaptiveMetrics : AdaptiveMetrics() {

    private val homeLottieContentScale
        @Composable
        get() = windowSizeClassWhen(
            compact = ContentScale.FillBounds,
            medium = ContentScale.FillBounds,
            expanded = ContentScale.Crop,
        )
    val LocalHomeLottieContentScale = requiredCompositionLocalOf<ContentScale>()

    @Suppress("MagicNumber")
    private val homeLottieAspectRatio
        @Composable
        get() = windowSizeClassWhen(
            compact = 2f,
            medium = 4f,
            expanded = 10f,
        )
    val LocalHomeLottieAspectRatio = requiredCompositionLocalOf<Float>()

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf(
        LocalHomeLottieContentScale provides homeLottieContentScale,
        LocalHomeLottieAspectRatio provides homeLottieAspectRatio,
    )
}

@Composable
internal fun HomeCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = HomeAdaptiveMetrics,
        content = content,
    )
