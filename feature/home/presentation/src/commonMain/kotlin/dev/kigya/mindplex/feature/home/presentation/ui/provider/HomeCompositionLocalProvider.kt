package dev.kigya.mindplex.feature.home.presentation.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider

internal object HomeAdaptiveMetrics : AdaptiveMetrics() {

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf()
}

@Composable
internal fun HomeCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = HomeAdaptiveMetrics,
        content = content,
    )
