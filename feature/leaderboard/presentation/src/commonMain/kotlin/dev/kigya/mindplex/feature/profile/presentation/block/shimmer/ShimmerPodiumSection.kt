package dev.kigya.mindplex.feature.profile.presentation.block.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.common.extension.fadeInEffect
import dev.kigya.mindplex.feature.profile.presentation.block.PodiumConstants
import dev.kigya.mindplex.feature.profile.presentation.ui.provider.LeaderboardAdaptiveMetrics.LocalPodiumFraction
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.LeaderboardTheme.firstRankCardColor
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.LeaderboardTheme.secondRankCardColor
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.LeaderboardTheme.thirdRankCardColor

@Composable
@Suppress("MagicNumber")
internal fun ShimmerPodiumSection(modifier: Modifier = Modifier) {
    val podiumFraction = LocalPodiumFraction.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(podiumFraction),
    ) {
        ShimmerUserScoreCard(
            modifier = modifier
                .fillMaxHeight(PodiumConstants.FIRST_RANK_CARD_FRACTION)
                .align(Alignment.TopCenter)
                .fadeInEffect(delayMillis = 900),
            isFirstRank = true,
            topColumnGradientColor = LeaderboardTheme.colorScheme.firstRankCardColor.value,
        )

        ShimmerUserScoreCard(
            modifier = modifier
                .fillMaxHeight(PodiumConstants.SECOND_RANK_CARD_FRACTION)
                .align(Alignment.BottomStart)
                .padding(bottom = LeaderboardTheme.dimension.dp36.value)
                .fadeInEffect(delayMillis = 600),
            isFirstRank = false,
            topColumnGradientColor = LeaderboardTheme.colorScheme.secondRankCardColor.value,
        )

        ShimmerUserScoreCard(
            modifier = modifier
                .fillMaxHeight(PodiumConstants.THIRD_RANK_CARD_FRACTION)
                .align(Alignment.BottomEnd)
                .fadeInEffect(delayMillis = 300),
            isFirstRank = false,
            topColumnGradientColor = LeaderboardTheme.colorScheme.thirdRankCardColor.value,
        )
    }
}
