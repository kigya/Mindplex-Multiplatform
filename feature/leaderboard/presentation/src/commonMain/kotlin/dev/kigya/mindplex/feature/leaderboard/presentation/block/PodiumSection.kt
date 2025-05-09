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
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.provider.LeaderboardAdaptiveMetrics.LocalPodiumFraction
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardBranchesTint
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardFirstRankCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardSecondRankCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardThirdRankCardColor
import kotlinx.collections.immutable.ImmutableList
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_branches

internal object PodiumConstants {
    const val FIRST_RANK_CARD_FRACTION = 0.7f
    const val SECOND_RANK_CARD_FRACTION = 0.53f
    const val THIRD_RANK_CARD_FRACTION = 0.47f
}

@Composable
@Suppress("MagicNumber")
internal fun PodiumSection(
    podiumUsers: ImmutableList<LeaderboardContract.State.UserCardData>,
    modifier: Modifier = Modifier,
) {
    val leaderboardWidthRatio = LocalLeaderboardWidthRatio.current
    val podiumFraction = LocalPodiumFraction.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(podiumFraction),
    ) {
        MindplexIcon(
            modifier = modifier
                .graphicsLayer {
                    scaleX = leaderboardWidthRatio
                    scaleY = leaderboardWidthRatio
                }
                .fillMaxWidth(leaderboardWidthRatio)
                .align(alignment = Alignment.Center)
                .fadeInEffect(),
            resource = Res.drawable.ic_branches,
            color = LeaderboardTheme.colorScheme.leaderboardBranchesTint,
        )

        if (podiumUsers.isNotEmpty()) {
            UserScoreCard(
                modifier = modifier
                    .fillMaxHeight(PodiumConstants.FIRST_RANK_CARD_FRACTION)
                    .align(Alignment.TopCenter)
                    .fadeInEffect(delayMillis = 900),
                state = podiumUsers[0],
                isFirstRank = true,
                topColumnGradientColor = LeaderboardTheme.colorScheme.leaderboardFirstRankCardColor.value,
            )

            if (podiumUsers.size >= 2) {
                UserScoreCard(
                    modifier = modifier
                        .fillMaxHeight(PodiumConstants.SECOND_RANK_CARD_FRACTION)
                        .align(Alignment.BottomStart)
                        .padding(bottom = LeaderboardTheme.dimension.dp36.value)
                        .fadeInEffect(delayMillis = 600),
                    state = podiumUsers[1],
                    isFirstRank = false,
                    topColumnGradientColor = LeaderboardTheme.colorScheme.leaderboardSecondRankCardColor.value,
                )
            }

            if (podiumUsers.size >= 3) {
                UserScoreCard(
                    modifier = modifier
                        .fillMaxHeight(PodiumConstants.THIRD_RANK_CARD_FRACTION)
                        .align(Alignment.BottomEnd)
                        .fadeInEffect(delayMillis = 300),
                    state = podiumUsers[2],
                    isFirstRank = false,
                    topColumnGradientColor = LeaderboardTheme.colorScheme.leaderboardThirdRankCardColor.value,
                )
            }
        }
    }
}
