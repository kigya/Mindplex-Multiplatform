package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract

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
@Suppress("UnusedParameter")
internal fun LeaderboardScreenContent(
    state: LeaderboardContract.State,
    event: (LeaderboardContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        MindplexText(
            text = "Leaderboard Screen",
            color = Color.White,
        )
    }
}
