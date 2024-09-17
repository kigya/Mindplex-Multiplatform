package dev.kigya.mindplex.feature.onboarding.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.util.lerp
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.component.MindplexButton
import dev.kigya.mindplex.core.presentation.component.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.component.MindplexJumpingDotsIndicator
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.util.extension.ifNotEmpty
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingBackground
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingButton
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingDescription
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingNextButtonContainer
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingNextButtonContent
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingSkipButtonContent
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingTitle
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

internal const val LOTTIE_WIDTH_PROPORTIONAL_DIVIDER = 2.1f
internal const val LOTTIE_ASPECT_RATIO = 2.24f
private const val PAGER_SCROLL_ANIMATION_DURATION_MILLIS = 500
private const val LOTTIE_SCALE_LERP_END = 0.5f

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
                .background(MindplexTheme.colorScheme.onboardingBackground)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(vertical = MindplexTheme.dimension.dp24)
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
private fun ImmutableList<OnboardingContract.State.OnboardingScreenData>.OnboardingPager(
    pagerState: PagerState,
    state: OnboardingContract.State,
) {
    val pageOffset by remember {
        derivedStateOf { abs(pagerState.currentPageOffsetFraction) }
    }
    val scale by remember {
        derivedStateOf { lerp(1f, LOTTIE_SCALE_LERP_END, pageOffset) }
    }

    MindplexHorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .background(MindplexTheme.colorScheme.onboardingBackground)
            .testTag("onboarding_pager"),
        pagerState = pagerState,
    ) { page ->
        val currentOnboardingScreenData = remember(page) { get(page) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("onboarding_page_$page"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            MindplexSpacer(size = MindplexTheme.dimension.dp12)
            OnboardingLottie(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .testTag("onboarding_lottie_$page"),
                drawableResource = currentOnboardingScreenData.lottieDrawableResource,
                lottiePath = currentOnboardingScreenData.lottiePath,
            )
            MindplexSpacer(size = MindplexTheme.dimension.dp36)
            AnimatedVisibility(visible = state.shouldDisplayTitle) {
                currentOnboardingScreenData.titleTextResource?.let { titleResource ->
                    MindplexText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MindplexTheme.dimension.dp24)
                            .testTag("onboarding_title_$page"),
                        text = stringResource(titleResource),
                        style = MindplexTheme.typography.onboardingTitle,
                        color = MindplexTheme.colorScheme.onboardingTitle,
                    )
                }
            }
            MindplexSpacer(size = MindplexTheme.dimension.dp8)
            AnimatedVisibility(visible = state.shouldDisplayDescription) {
                currentOnboardingScreenData.descriptionTextResource?.let { descriptionResource ->
                    MindplexText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MindplexTheme.dimension.dp24)
                            .testTag("onboarding_description_$page"),
                        text = stringResource(descriptionResource),
                        style = MindplexTheme.typography.onboardingDescription,
                        color = MindplexTheme.colorScheme.onboardingDescription,
                    )
                }
            }
            MindplexSpacer(size = MindplexTheme.dimension.dp24)
        }
    }
}

@Composable
private fun ColumnScope.OnboardingPagerDotsIndicator(
    state: OnboardingContract.State,
    pagerState: PagerState,
) {
    MindplexSpacer(size = MindplexTheme.dimension.dp24)
    AnimatedVisibility(visible = state.shouldDisplayDotsIndicator) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("onboarding_dots_indicator"),
            horizontalArrangement = Arrangement.Center,
        ) {
            MindplexJumpingDotsIndicator(pagerState = pagerState)
            MindplexSpacer(size = MindplexTheme.dimension.dp64)
        }
    }
}

@Composable
private fun ColumnScope.OnboardingButtons(
    pagerState: PagerState,
    onboardingScreenData: ImmutableList<OnboardingContract.State.OnboardingScreenData>,
    event: (OnboardingContract.Event) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexSpacer(size = MindplexTheme.dimension.dp36)
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
                .padding(horizontal = MindplexTheme.dimension.dp24),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            onboardingScreenData[page].skipButtonTextResource?.let { skipResource ->
                AnimatedVisibility(
                    modifier = Modifier.weight(1f),
                    visible = page < onboardingScreenData.size,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally(),
                ) {
                    MindplexButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("onboarding_skip_button_$page"),
                        labelText = stringResource(skipResource),
                        textStyle = MindplexTheme.typography.onboardingButton,
                        contentColor = MindplexTheme.colorScheme.onboardingSkipButtonContent,
                    ) {
                        event(OnboardingContract.Event.OnSkipClicked)
                        performClickHapticFeedback(hapticFeedback)
                    }
                }
                if (page < onboardingScreenData.size) {
                    MindplexSpacer(size = MindplexTheme.dimension.dp24)
                }
            }
            onboardingScreenData[page].nextButtonTextResource?.let { nextResource ->
                MindplexButton(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("onboarding_next_button_$page"),
                    labelText = stringResource(nextResource),
                    textStyle = MindplexTheme.typography.onboardingButton,
                    containerColor = MindplexTheme.colorScheme.onboardingNextButtonContainer,
                    contentColor = MindplexTheme.colorScheme.onboardingNextButtonContent,
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
