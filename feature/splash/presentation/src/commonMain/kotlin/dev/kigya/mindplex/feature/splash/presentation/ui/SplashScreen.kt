package dev.kigya.mindplex.feature.splash.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.component.MindplexLottie
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexSpacerSize
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.resources.ResourceProvider
import dev.kigya.mindplex.core.util.compose.LaunchedEffectSaveable
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import org.jetbrains.compose.resources.stringResource

internal const val LOTTIE_WIDTH_PROPORTIONAL_DIVIDER = 3f

@Composable
fun SplashScreen(
    contract: SplashContract,
) {
    val (state, event, _) = use(contract)

    SplashScreenContent(
        state = state,
        event = event,
    )
}

@Composable
private fun SplashScreenContent(
    state: SplashContract.State,
    event: (SplashContract.Event) -> Unit,
) {
    LaunchedEffectSaveable(Unit) { event(SplashContract.Event.OnFirstLaunch) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MindplexLottie(
            modifier = Modifier.size(getLottieSplashSize()),
            reader = { with(ResourceProvider.Lottie) { loadAsByteArray(mindplexLogo) } },
            speed = 1.5f,
            onFinish = { event(SplashContract.Event.OnAnimationFinished) }
        )
        AnimatedVisibility(
            visible = state.shouldDisplayText,
            enter = fadeIn(animationSpec = tween(700)) + expandVertically(animationSpec = tween(700))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                MindplexSpacer(size = MindplexSpacerSize.MEDIUM)
                MindplexText(
                    text = stringResource(ResourceProvider.Strings.splashTitle),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}

@Composable
internal expect fun getLottieSplashSize(): Dp
