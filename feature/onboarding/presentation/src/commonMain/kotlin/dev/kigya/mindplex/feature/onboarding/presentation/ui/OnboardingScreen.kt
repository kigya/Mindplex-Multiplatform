package dev.kigya.mindplex.feature.onboarding.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.util.lerp
import dev.kigya.mindplex.core.presentation.component.MindplexButton
import dev.kigya.mindplex.core.presentation.component.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.component.MindplexJumpingDotsIndicator
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexSpacerSize
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing
import dev.kigya.mindplex.core.util.compose.LaunchedEffectSaveable
import dev.kigya.mindplex.core.util.compose.StableFlow
import dev.kigya.mindplex.core.util.compose.performClickHapticFeedback
import dev.kigya.mindplex.core.util.extension.ifNotEmpty
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.model.OnboardingScreenUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingScreenContent(
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
                .background(MaterialTheme.colorScheme.onSecondaryContainer)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(vertical = MaterialTheme.spacing.large),
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
                onboardingScreenUiModels = this@ifNotEmpty,
                event = event,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ImmutableList<OnboardingScreenUiModel>.OnboardingPager(
    pagerState: PagerState,
    state: OnboardingContract.State,
) {
    MindplexHorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondaryContainer),
        pagerState = pagerState,
    ) { page ->
        val currentOnboardingScreenData = remember(page) { get(page) }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            MindplexSpacer(size = MindplexSpacerSize.SMALL)
            OnboardingLottie(
                drawableResource = currentOnboardingScreenData.lottieDrawableResource,
                lottiePath = currentOnboardingScreenData.lottiePath,
            )
            MindplexSpacer(size = MindplexSpacerSize.EXTRA_LARGE)
            AnimatedVisibility(visible = state.shouldDisplayTitle) {
                currentOnboardingScreenData.titleTextResource?.let { titleResource ->
                    MindplexText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.large),
                        text = stringResource(titleResource),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
            MindplexSpacer(size = MindplexSpacerSize.EXTRA_SMALL)
            AnimatedVisibility(visible = state.shouldDisplayDescription) {
                currentOnboardingScreenData.descriptionTextResource?.let { descriptionResource ->
                    MindplexText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.large),
                        text = stringResource(descriptionResource),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
            MindplexSpacer(size = MindplexSpacerSize.LARGE)
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ColumnScope.OnboardingPagerDotsIndicator(
    state: OnboardingContract.State,
    pagerState: PagerState,
) {
    MindplexSpacer(size = MindplexSpacerSize.LARGE)
    AnimatedVisibility(visible = state.shouldDisplayDotsIndicator) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            MindplexJumpingDotsIndicator(pagerState = pagerState)
            MindplexSpacer(size = MindplexSpacerSize.GIANT)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.OnboardingButtons(
    pagerState: PagerState,
    onboardingScreenUiModels: ImmutableList<OnboardingScreenUiModel>,
    event: (OnboardingContract.Event) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexSpacer(size = MindplexSpacerSize.EXTRA_LARGE)
    AnimatedContent(
        targetState = pagerState.currentPage,
        transitionSpec = {
            if ((targetState == 0 || targetState == 1) && (initialState == 0 || initialState == 1)) {
                fadeIn(animationSpec = tween(0)) togetherWith fadeOut(animationSpec = tween(0))
            } else if (targetState > initialState) {
                (
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                        slideOutHorizontally { width -> -width } + fadeOut()
                    ).using(SizeTransform(clip = false))
            } else {
                (
                    slideInHorizontally { width -> -width } + fadeIn() togetherWith
                        slideOutHorizontally { width -> width } + fadeOut()
                    ).using(SizeTransform(clip = false))
            }
        },
    ) { page ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.large),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            onboardingScreenUiModels[page].skipButtonTextResource?.let { skipResource ->
                AnimatedVisibility(
                    modifier = Modifier.weight(1f),
                    visible = page < onboardingScreenUiModels.size,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally(),
                ) {
                    MindplexButton(
                        modifier = Modifier.fillMaxWidth(),
                        labelText = stringResource(skipResource),
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    ) {
                        event(OnboardingContract.Event.OnSkipClicked)
                        performClickHapticFeedback(hapticFeedback)
                    }
                }
                if (page < onboardingScreenUiModels.size) {
                    MindplexSpacer(size = MindplexSpacerSize.LARGE)
                }
            }
            onboardingScreenUiModels[page].nextButtonTextResource?.let { nextResource ->
                MindplexButton(
                    modifier = Modifier.weight(1f),
                    labelText = stringResource(nextResource),
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ) {
                    event(OnboardingContract.Event.OnNextClicked(pagerState.currentPage))
                    performClickHapticFeedback(hapticFeedback)
                }
            }
        }
    }
}

@Composable
expect fun OnboardingLottie(
    modifier: Modifier = Modifier,
    drawableResource: DrawableResource?,
    lottiePath: String?,
)
