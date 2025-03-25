@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.feature.leaderboard.presentation.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.layout.ContentScale
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.common.util.requiredCompositionLocalOf
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider

internal object LeaderboardAdaptiveMetrics : AdaptiveMetrics() {

    private val leaderboardLottieContentScale
        @Composable
        get() = windowSizeClassWhen(
            compact = ContentScale.FillBounds,
            medium = ContentScale.FillBounds,
            expanded = ContentScale.Crop,
        )
    val LocalLeaderboardLottieContentScale = requiredCompositionLocalOf<ContentScale>()

    @Suppress("MagicNumber")
    private val leaderboardLottieAspectRatio
        @Composable
        get() = windowSizeClassWhen(
            compact = 2f,
            medium = 4f,
            expanded = 10f,
        )
    val LocalLeaderBoardLottieAspectRatio = requiredCompositionLocalOf<Float>()

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf(
        LocalLeaderboardLottieContentScale provides leaderboardLottieContentScale,
        LocalLeaderBoardLottieAspectRatio provides leaderboardLottieAspectRatio,
    )
}

@Composable
internal fun LeaderboardCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = LeaderboardAdaptiveMetrics,
        content = content,
    )
