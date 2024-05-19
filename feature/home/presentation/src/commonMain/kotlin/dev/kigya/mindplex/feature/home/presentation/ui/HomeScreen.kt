package dev.kigya.mindplex.feature.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.util.compose.LaunchedEffectSaveable
import dev.kigya.mindplex.core.util.compose.StableFlow
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract

@Composable
fun HomeScreen(contract: HomeContract) {
    val (state, event, effect) = use(contract)

    HomeScreenContent(
        state = state,
        event = event,
        effect = effect,
    )
}

@Composable
@Suppress("UnusedParameter")
private fun HomeScreenContent(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
    effect: StableFlow<HomeContract.Effect>,
) {
    LaunchedEffectSaveable(Unit) { event(HomeContract.Event.OnFirstLaunch) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Home")
    }
}
