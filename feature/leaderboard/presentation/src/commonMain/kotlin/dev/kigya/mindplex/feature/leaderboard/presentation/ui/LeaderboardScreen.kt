package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

private const val LANDSCAPE_PODIUM_WIDTH = 0.43f

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
                LeaderboardPortraitSection(state = state)
            },
            landscape = {
                LeaderboardLandscapeSection(state = state)
            },
        )
    }
}

@Composable
private fun ColumnScope.LeaderboardPortraitSection(state: LeaderboardContract.State) {
    LeaderBoardScreenHeader(modifier = Modifier.fillMaxWidth())

    MindplexSpacer(size = LeaderboardTheme.dimension.dp36)

    PodiumSection(
        podiumUsers = state.userCardData,
        leaderboardLoading = state.leaderboardLoading,
    )

    MindplexSpacer(size = LeaderboardTheme.dimension.dp36)

    UserPlaceSection(
        userPlaces = state.userCardData,
        leaderboardLoading = state.leaderboardLoading,
    )
}

@Composable
private fun ColumnScope.LeaderboardLandscapeSection(state: LeaderboardContract.State) {
    LeaderBoardScreenHeader(modifier = Modifier.fillMaxWidth())

    MindplexSpacer(size = LeaderboardTheme.dimension.dp36)

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(LeaderboardTheme.dimension.dp36.value),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PodiumSection(
            modifier = Modifier.fillMaxWidth(LANDSCAPE_PODIUM_WIDTH),
            podiumUsers = state.userCardData,
            leaderboardLoading = state.leaderboardLoading,
        )

        UserPlaceSection(
            modifier = Modifier.weight(1f),
            userPlaces = state.userCardData,
            leaderboardLoading = state.leaderboardLoading,
        )
    }
}
