package dev.kigya.mindplex.feature.leaderboard.presentation.block.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.common.extension.fadeInEffect
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.firstPlaceCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.secondPlaceCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.thirdPlaceCardColor

private const val PODIUM_FRACTION = 0.5f
private const val FIRST_PLACE_CARD_FRACTION = 0.7f
private const val SECOND_PLACE_CARD_FRACTION = 0.5f
private const val THIRD_PLACE_CARD_FRACTION = 0.4f

@Composable
@Suppress("MagicNumber")
internal fun ShimmerPodiumSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(PODIUM_FRACTION),
    ) {
        ShimmerUserScoreCard(
            modifier = modifier
                .fillMaxHeight(FIRST_PLACE_CARD_FRACTION)
                .align(Alignment.TopCenter)
                .fadeInEffect(delayMillis = 900),
            isFirstPlace = true,
            topColumnGradientColor = LeaderboardTheme.colorScheme.firstPlaceCardColor.value,
        )

        ShimmerUserScoreCard(
            modifier = modifier
                .fillMaxHeight(SECOND_PLACE_CARD_FRACTION)
                .align(Alignment.BottomStart)
                .padding(bottom = LeaderboardTheme.dimension.dp36.value)
                .fadeInEffect(delayMillis = 600),
            isFirstPlace = false,
            topColumnGradientColor = LeaderboardTheme.colorScheme.secondPlaceCardColor.value,
        )

        ShimmerUserScoreCard(
            modifier = modifier
                .fillMaxHeight(THIRD_PLACE_CARD_FRACTION)
                .align(Alignment.BottomEnd)
                .fadeInEffect(delayMillis = 300),
            isFirstPlace = false,
            topColumnGradientColor = LeaderboardTheme.colorScheme.thirdPlaceCardColor.value,
        )
    }
}
