package dev.kigya.mindplex.feature.onboarding.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.util.extension.ifNotEmpty
import dev.kigya.mindplex.feature.onboarding.presentation.component.OnboardingButtons
import dev.kigya.mindplex.feature.onboarding.presentation.component.OnboardingPager
import dev.kigya.mindplex.feature.onboarding.presentation.component.OnboardingPagerDotsIndicator
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingBackground
import org.jetbrains.compose.resources.DrawableResource

internal const val LOTTIE_WIDTH_PROPORTIONAL_DIVIDER = 2.1f
internal const val LOTTIE_ASPECT_RATIO = 2.24f
private const val PAGER_SCROLL_ANIMATION_DURATION_MILLIS = 500

@Composable
fun OnboardingScreen(contract: OnboardingContract) {
    val (state, event, effect) = use(contract)

    OnboardingScreenContent(
        state = state,
        event = event,
        effect = effect,
    )
}

@Composable
@VisibleForTesting
internal fun OnboardingScreenContent(
    state: OnboardingContract.State,
    event: (OnboardingContract.Event) -> Unit,
    effect: StableFlow<OnboardingContract.Effect>,
) {
    LaunchedEffectSaveable(Unit) { event(OnboardingContract.Event.OnFirstLaunch) }

    val pagerState = rememberPagerState(pageCount = state.onboardingData::size)

    LaunchedEffect(effect) {
        effect.value.collect { onboardingEffect ->
            when (onboardingEffect) {
                is OnboardingContract.Effect.ScrollToPage ->
                    pagerState.animateScrollToPage(
                        page = onboardingEffect.pageTo,
                        animationSpec = tween(
                            durationMillis = PAGER_SCROLL_ANIMATION_DURATION_MILLIS,
                            easing = FastOutSlowInEasing,
                        ),
                    )
            }
        }
    }

    state.onboardingData.ifNotEmpty {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(OnboardingTheme.colorScheme.onboardingBackground)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(vertical = OnboardingTheme.dimension.dp24)
                .testTag("onboarding_content"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            OnboardingPager(
                pagerState = pagerState,
                state = state,
            )
            OnboardingPagerDotsIndicator(
                state = state,
                pagerState = pagerState,
            )
            OnboardingButtons(
                pagerState = pagerState,
                onboardingScreenData = this@ifNotEmpty,
                event = event,
            )
        }
    }
}

@Composable
expect fun OnboardingLottie(
    modifier: Modifier = Modifier,
    drawableResource: DrawableResource?,
    lottiePath: String?,
)
