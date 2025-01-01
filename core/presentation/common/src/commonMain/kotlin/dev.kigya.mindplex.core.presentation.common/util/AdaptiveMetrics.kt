package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.window.core.layout.WindowWidthSizeClass
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindowWidth

abstract class AdaptiveMetrics {

    @Composable
    abstract fun provideValues(): Array<ProvidedValue<*>>

    @Composable
    fun <T> windowSizeClassWhen(
        compact: T,
        medium: T,
        expanded: T,
    ): T = when (LocalWindowWidth.current) {
        WindowWidthSizeClass.COMPACT -> compact
        WindowWidthSizeClass.MEDIUM -> medium
        else -> expanded
    }
}

@Composable
fun MindplexAdaptiveContainer(
    portrait: @Composable () -> Unit,
    landscape: @Composable () -> Unit,
) = if (LocalWindowWidth.current == WindowWidthSizeClass.COMPACT) portrait() else landscape()
