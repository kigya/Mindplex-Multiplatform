package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import dev.kigya.mindplex.core.presentation.common.extension.fadeInEffect
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.provider.LeaderboardAdaptiveMetrics.LocalLeaderboardWidthRatio
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.branchesTint
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.firstPlaceCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.secondPlaceCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.thirdPlaceCardColor
import kotlinx.collections.immutable.ImmutableList
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_branches

private const val PODIUM_FRACTION = 0.5f
private const val FIRST_PLACE_CARD_FRACTION = 0.7f
private const val SECOND_PLACE_CARD_FRACTION = 0.5f
private const val THIRD_PLACE_CARD_FRACTION = 0.4f

@Composable
@Suppress("MagicNumber")
internal fun PodiumSection(
    podiumUsers: ImmutableList<LeaderboardContract.State.UserCardData>,
    modifier: Modifier = Modifier,
) {
    val leaderboardWidthRatio = LocalLeaderboardWidthRatio.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(PODIUM_FRACTION),
    ) {
        MindplexIcon(
            modifier = modifier
                .fillMaxWidth(leaderboardWidthRatio)
                .align(alignment = Alignment.Center)
                .fadeInEffect()
                .graphicsLayer {
                    scaleX = leaderboardWidthRatio
                    scaleY = leaderboardWidthRatio
                },
            resource = Res.drawable.ic_branches,
            color = LeaderboardTheme.colorScheme.branchesTint,
        )

        if (podiumUsers.isNotEmpty()) {
            UserScoreCard(
                modifier = modifier
                    .fillMaxHeight(FIRST_PLACE_CARD_FRACTION)
                    .align(Alignment.TopCenter)
                    .fadeInEffect(delayMillis = 900),
                state = podiumUsers[0],
                isFirstPlace = true,
                topColumnGradientColor = LeaderboardTheme.colorScheme.firstPlaceCardColor.value,
            )

            if (podiumUsers.size >= 2) {
                UserScoreCard(
                    modifier = modifier
                        .fillMaxHeight(SECOND_PLACE_CARD_FRACTION)
                        .align(Alignment.BottomStart)
                        .padding(bottom = LeaderboardTheme.dimension.dp36.value)
                        .fadeInEffect(delayMillis = 600),
                    state = podiumUsers[1],
                    isFirstPlace = false,
                    topColumnGradientColor = LeaderboardTheme.colorScheme.secondPlaceCardColor.value,
                )
            }

            if (podiumUsers.size >= 3) {
                UserScoreCard(
                    modifier = modifier
                        .fillMaxHeight(THIRD_PLACE_CARD_FRACTION)
                        .align(Alignment.BottomEnd)
                        .fadeInEffect(delayMillis = 300),
                    state = podiumUsers[2],
                    isFirstPlace = false,
                    topColumnGradientColor = LeaderboardTheme.colorScheme.thirdPlaceCardColor.value,
                )
            }
        }
    }
}
