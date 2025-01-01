package dev.kigya.mindplex.core.presentation.feature.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics

@Composable
fun <T : AdaptiveMetrics> PlatformCompositionLocalProvider(
    metrics: T,
    vararg values: ProvidedValue<*>,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        values = metrics.provideValues() + values,
        content = content,
    )
}
