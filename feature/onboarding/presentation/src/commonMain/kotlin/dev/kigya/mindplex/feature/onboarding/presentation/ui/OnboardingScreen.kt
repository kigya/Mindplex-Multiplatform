package dev.kigya.mindplex.feature.onboarding.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.kigya.mindplex.core.presentation.common.util.MindplexAdaptiveContainer
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.util.extension.ifNotEmpty
import dev.kigya.mindplex.feature.onboarding.presentation.block.OnboardingButtons
import dev.kigya.mindplex.feature.onboarding.presentation.block.OnboardingDescription
import dev.kigya.mindplex.feature.onboarding.presentation.block.OnboardingLottie
import dev.kigya.mindplex.feature.onboarding.presentation.block.OnboardingTitle
import dev.kigya.mindplex.feature.onboarding.presentation.block.container.OnboardingContainer
import dev.kigya.mindplex.feature.onboarding.presentation.block.container.OnboardingContentLandscapeContainer
import dev.kigya.mindplex.feature.onboarding.presentation.block.container.OnboardingControlsContainerLandscape
import dev.kigya.mindplex.feature.onboarding.presentation.block.container.OnboardingPagerPortraitContainer
import dev.kigya.mindplex.feature.onboarding.presentation.block.pager.OnboardingPager
import dev.kigya.mindplex.feature.onboarding.presentation.block.pager.OnboardingPagerDotsIndicator
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.provider.OnboardingCompositionLocalProvider
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme

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

    OnboardingCompositionLocalProvider {
        state.onboardingData.ifNotEmpty {
            OnboardingContainer {
                MindplexAdaptiveContainer(
                    portrait = {
                        OnboardingContentPortraitVariant(
                            state = state,
                            pagerState = pagerState,
                            event = event,
                        )
                    },
                    landscape = {
                        OnboardingContentLandscapeVariant(
                            state = state,
                            pagerState = pagerState,
                            event = event,
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.OnboardingContentPortraitVariant(
    state: OnboardingContract.State,
    pagerState: PagerState,
    event: (OnboardingContract.Event) -> Unit,
) {
    OnboardingPager(
        pagerState = pagerState,
        orientation = Orientation.Horizontal,
    ) { page ->
        OnboardingPagerPortraitContainer {
            MindplexSpacer(size = OnboardingTheme.dimension.dp12)

            OnboardingLottie(
                lottiePath = state.onboardingData[page].lottiePath,
                pagerState = pagerState,
                orientation = Orientation.Vertical,
            )

            MindplexSpacer(size = OnboardingTheme.dimension.dp36)

            OnboardingTitle(
                state = state,
                page = page,
            )

            MindplexSpacer(size = OnboardingTheme.dimension.dp8)

            OnboardingDescription(
                state = state,
                page = page,
            )

            MindplexSpacer(size = OnboardingTheme.dimension.dp24)
        }
    }

    MindplexSpacer(size = OnboardingTheme.dimension.dp24)

    OnboardingPagerDotsIndicator(
        state = state,
        orientation = Orientation.Horizontal,
        pagerState = pagerState,
    )

    OnboardingButtons(
        state = state,
        pagerState = pagerState,
        event = event,
    )
}

@Composable
private fun OnboardingContentLandscapeVariant(
    state: OnboardingContract.State,
    pagerState: PagerState,
    event: (OnboardingContract.Event) -> Unit,
) {
    OnboardingContentLandscapeContainer {
        OnboardingPager(
            pagerState = pagerState,
            orientation = Orientation.Vertical,
        ) { page ->
            OnboardingLottie(
                lottiePath = state.onboardingData[page].lottiePath,
                pagerState = pagerState,
                orientation = Orientation.Horizontal,
            )
        }

        OnboardingControlsContainerLandscape {
            OnboardingTitle(
                state = state,
                page = pagerState.currentPage,
            )

            MindplexSpacer(size = OnboardingTheme.dimension.dp8)

            OnboardingDescription(
                state = state,
                page = pagerState.currentPage,
            )

            MindplexSpacer(size = OnboardingTheme.dimension.dp24)

            OnboardingButtons(
                state = state,
                pagerState = pagerState,
                event = event,
            )
        }

        OnboardingPagerDotsIndicator(
            state = state,
            orientation = Orientation.Vertical,
            pagerState = pagerState,
        )
    }
}
