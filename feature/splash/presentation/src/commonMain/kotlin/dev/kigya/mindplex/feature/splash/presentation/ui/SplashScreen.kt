package dev.kigya.mindplex.feature.splash.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.feature.splash.presentation.block.SplashContainer
import dev.kigya.mindplex.feature.splash.presentation.block.SplashLogo
import dev.kigya.mindplex.feature.splash.presentation.block.SplashText
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.feature.splash.presentation.ui.provider.SplashCompositionLocalProvider

@Composable
fun SplashScreen(contract: SplashContract) {
    val (state, event, _) = use(contract)

    SplashScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
internal fun SplashScreenContent(
    state: SplashContract.State,
    event: (SplashContract.Event) -> Unit,
) = SplashCompositionLocalProvider {
    SplashContainer {
        SplashLogo(event)
        SplashText(state)
    }
}
