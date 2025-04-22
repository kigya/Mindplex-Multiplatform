package dev.kigya.mindplex.feature.game.presentation.block.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract

@Composable
internal fun GameTopBar(
    state: GameContract.State,
    event: (GameContract.Event) -> Unit,
) {
    Box {
        if (state.isLoading) {
            GameTopBarShimmer()
        } else {
            AnimatedVisibility(
                visible = !state.isLoading,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            ) {
                GameTopBarContent(
                    state = state,
                    event = event,
                )
            }
        }
    }
}
