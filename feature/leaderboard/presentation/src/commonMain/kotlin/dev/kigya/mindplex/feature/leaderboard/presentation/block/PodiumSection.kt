package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.branchesTint
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.firstPlaceCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.secondPlaceCardColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.thirdPlaceCardColor
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_branches

private const val PODIUM_HEIGHT = 0.5f
private const val FIRST_PLACE_CARD_HEIGHT = 0.7f
private const val SECOND_PLACE_CARD_HEIGHT = 0.5f
private const val THIRD_PLACE_CARD_HEIGHT = 0.4f
private const val THIRD_CARD = 3

@Composable
internal fun PodiumSection(
    modifier: Modifier = Modifier,
    podiumUsers: List<LeaderboardContract.State.UserCardData>,
    event: (LeaderboardContract.Event) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(PODIUM_HEIGHT),
    ) {
        MindplexIcon(
            modifier = modifier.align(alignment = Alignment.Center),
            resource = Res.drawable.ic_branches,
            color = LeaderboardTheme.colorScheme.branchesTint,
        )

        if (podiumUsers.isNotEmpty()) {
            UserScoreCard(
                modifier = modifier
                    .fillMaxHeight(FIRST_PLACE_CARD_HEIGHT)
                    .align(Alignment.TopCenter),
                state = podiumUsers[0],
                event = event,
                isFirstPlace = true,
                topColumnGradientColor = LeaderboardTheme.colorScheme.firstPlaceCardColor.value,
            )

            if (podiumUsers.size >= 2) {
                UserScoreCard(
                    modifier = modifier
                        .fillMaxHeight(SECOND_PLACE_CARD_HEIGHT)
                        .align(Alignment.BottomStart)
                        .padding(bottom = LeaderboardTheme.dimension.dp36.value),
                    state = podiumUsers[1],
                    event = event,
                    isFirstPlace = false,
                    topColumnGradientColor = LeaderboardTheme.colorScheme.secondPlaceCardColor.value,
                )
            }

            if (podiumUsers.size >= THIRD_CARD) {
                UserScoreCard(
                    modifier = modifier
                        .fillMaxHeight(THIRD_PLACE_CARD_HEIGHT)
                        .align(Alignment.BottomEnd),
                    state = podiumUsers[2],
                    event = event,
                    isFirstPlace = false,
                    topColumnGradientColor = LeaderboardTheme.colorScheme.thirdPlaceCardColor.value,
                )
            }
        }
    }
}
