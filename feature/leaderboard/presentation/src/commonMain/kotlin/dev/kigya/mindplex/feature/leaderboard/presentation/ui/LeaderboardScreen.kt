package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.common.util.MindplexAdaptiveContainer
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.uikit.MindplexErrorStubContainer
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.feature.leaderboard.presentation.block.LeaderBoardScreenHeader
import dev.kigya.mindplex.feature.leaderboard.presentation.block.PodiumSection
import dev.kigya.mindplex.feature.leaderboard.presentation.block.UserPlaceSection
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.provider.LeaderboardCompositionLocalProvider
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardBackground

@Composable
fun LeaderboardScreen(contract: LeaderboardContract) {
    val (state, event, _) = use(contract)

    LeaderboardScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
internal fun LeaderboardScreenContent(
    state: LeaderboardContract.State,
    event: (LeaderboardContract.Event) -> Unit,
) = LeaderboardCompositionLocalProvider {
    MindplexErrorStubContainer(
        background = LeaderboardTheme.colorScheme.leaderboardBackground,
        stubErrorType = state.stubErrorType,
        verticalArrangement = Arrangement.Top,
        onRetryButtonClicked = { event(LeaderboardContract.Event.OnErrorStubClicked) },
    ) {
        MindplexAdaptiveContainer(
            portrait = {
                LeaderboardPortraitSection(
                    state = state,
                    event = event,
                )
            },
            landscape = {
//            HomeLandscapeSection(
//                state = state,
//                event = event,
//            )
            },
        )
    }
}

@Composable
private fun ColumnScope.LeaderboardPortraitSection(
    state: LeaderboardContract.State,
    event: (LeaderboardContract.Event) -> Unit,
) {
    LeaderBoardScreenHeader(
        modifier = Modifier.fillMaxWidth(),
    )

    MindplexSpacer(size = LeaderboardTheme.dimension.dp36)

    PodiumSection(
        podiumUsers = state.userCardData,
        event = event,
    )

    MindplexSpacer(size = LeaderboardTheme.dimension.dp36)

    UserPlaceSection(
        userPlaces = state.userCardData,
        event = event,
    )
}
