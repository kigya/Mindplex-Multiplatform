package dev.kigya.mindplex.feature.onboarding.presentation.block

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.uikit.MindplexButton
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.provider.OnboardingAdaptiveMetrics.LocalOnboardingButtonsHorizontalPadding
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingButton
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingNextButtonBackground
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingNextButtonText
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingSkipButtonBackground
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingSkipButtonText
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.OnboardingButtons(
    state: OnboardingContract.State,
    pagerState: PagerState,
    event: (OnboardingContract.Event) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexSpacer(size = OnboardingTheme.dimension.dp36)

    AnimatedContent(
        targetState = pagerState.currentPage,
        transitionSpec = {
            if ((targetState == 0 || targetState == 1) && (initialState == 0 || initialState == 1)) {
                fadeIn(tween(0)) togetherWith fadeOut(tween(0))
            } else if (targetState > initialState) {
                (slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut())
                    .using(SizeTransform(clip = false))
            } else {
                (slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut())
                    .using(SizeTransform(clip = false))
            }
        },
    ) { page ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalOnboardingButtonsHorizontalPadding.current),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            state.onboardingData[page].skipButtonTextResource?.let { skipResource ->
                AnimatedVisibility(
                    visible = page < state.onboardingData.size,
                    modifier = Modifier.weight(1f),
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally(),
                ) {
                    MindplexButton(
                        text = stringResource(skipResource),
                        backgroundColor = OnboardingTheme.colorScheme.onboardingSkipButtonBackground,
                        borderColor = OnboardingTheme.colorScheme.onboardingSkipButtonBackground,
                        textColor = OnboardingTheme.colorScheme.onboardingSkipButtonText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("onboarding_skip_button_$page"),
                        textTypography = OnboardingTheme.typography.onboardingButton,
                    ) {
                        event(OnboardingContract.Event.OnSkipClicked)
                        performClickHapticFeedback(hapticFeedback)
                    }
                }
                if (page < state.onboardingData.size) {
                    MindplexSpacer(size = OnboardingTheme.dimension.dp24)
                }
            }
            state.onboardingData[page].nextButtonTextResource?.let { nextResource ->
                MindplexButton(
                    text = stringResource(nextResource),
                    backgroundColor = OnboardingTheme.colorScheme.onboardingNextButtonBackground,
                    borderColor = OnboardingTheme.colorScheme.onboardingNextButtonBackground,
                    textColor = OnboardingTheme.colorScheme.onboardingNextButtonText,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("onboarding_next_button_$page"),
                    textTypography = OnboardingTheme.typography.onboardingButton,
                ) {
                    event(OnboardingContract.Event.OnNextClicked(pagerState.currentPage))
                    performClickHapticFeedback(hapticFeedback)
                }
            }
        }
    }
}
