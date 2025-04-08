@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.feature.leaderboard.presentation.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.common.util.requiredCompositionLocalOf
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider

internal object LeaderboardAdaptiveMetrics : AdaptiveMetrics() {

    private val LeaderboardBranchesWidthRatio
        @Composable
        get() = windowSizeClassWhen(
            compact = 1f,
            medium = 2f,
            expanded = 2.3f,
        )
    val LocalLeaderboardWidthRatio = requiredCompositionLocalOf<Float>()

    private val PodiumFraction
        @Composable
        get() = windowSizeClassWhen(
            compact = 0.5f,
            medium = 0.6f,
            expanded = 0.6f,
        )
    val LocalPodiumFraction = requiredCompositionLocalOf<Float>()

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf(
        LocalLeaderboardWidthRatio provides LeaderboardBranchesWidthRatio,
        LocalPodiumFraction provides PodiumFraction,
    )
}

@Composable
internal fun LeaderboardCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = LeaderboardAdaptiveMetrics,
        content = content,
    )
