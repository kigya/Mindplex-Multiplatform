package dev.kigya.mindplex.feature.splash.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.feature.splash.presentation.block.SplashContainer
import dev.kigya.mindplex.feature.splash.presentation.block.SplashLogo
import dev.kigya.mindplex.feature.splash.presentation.block.SplashText
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.feature.splash.presentation.ui.provider.SplashCompositionLocalProvider

@Composable
fun SplashScreen(contract: SplashContract) {
    val (state, event, effect) = use(contract)

    SplashScreenContent(
        state = state,
        event = event,
        effect = effect,
    )
}

@Composable
@VisibleForTesting
internal fun SplashScreenContent(
    state: SplashContract.State,
    event: (SplashContract.Event) -> Unit,
    effect: StableFlow<SplashContract.Effect>,
) = SplashCompositionLocalProvider {
    SplashContainer {
        val isSystemDark = isSystemInDarkTheme()

        LaunchedEffect(effect) {
            effect.value.collect { themeEffect ->
                when (themeEffect) {
                    is SplashContract.Effect.RequestSystemTheme ->
                        event(SplashContract.Event.OnSystemThemeReceived(isSystemDark))
                }
            }
        }

        SplashLogo(event)
        SplashText(state)
    }
}
