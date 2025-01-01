package dev.kigya.mindplex.feature.profile.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract

@Composable
fun ProfileScreen(contract: ProfileContract) {
    val (state, event, _) = use(contract)

    ProfileScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
@Suppress("UnusedParameter")
internal fun ProfileScreenContent(
    state: ProfileContract.State,
    event: (ProfileContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
    }
}
