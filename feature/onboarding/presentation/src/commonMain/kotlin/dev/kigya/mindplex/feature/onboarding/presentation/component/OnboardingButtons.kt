package dev.kigya.mindplex.feature.onboarding.presentation.component

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
import dev.kigya.mindplex.core.presentation.component.MindplexButton
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingButton
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingNextButtonContainer
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingNextButtonContent
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingSkipButtonContent
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.OnboardingButtons(
    pagerState: PagerState,
    onboardingScreenData: ImmutableList<OnboardingContract.State.OnboardingScreenData>,
    event: (OnboardingContract.Event) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexSpacer(size = OnboardingTheme.dimension.dp36)

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
                .padding(horizontal = OnboardingTheme.dimension.dp24),
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
                        textStyle = OnboardingTheme.typography.onboardingButton,
                        contentColor = OnboardingTheme.colorScheme.onboardingSkipButtonContent,
                    ) {
                        event(OnboardingContract.Event.OnSkipClicked)
                        performClickHapticFeedback(hapticFeedback)
                    }
                }
                if (page < onboardingScreenData.size) {
                    MindplexSpacer(size = OnboardingTheme.dimension.dp24)
                }
            }
            onboardingScreenData[page].nextButtonTextResource?.let { nextResource ->
                MindplexButton(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("onboarding_next_button_$page"),
                    labelText = stringResource(nextResource),
                    textStyle = OnboardingTheme.typography.onboardingButton,
                    containerColor = OnboardingTheme.colorScheme.onboardingNextButtonContainer,
                    contentColor = OnboardingTheme.colorScheme.onboardingNextButtonContent,
                ) {
                    event(OnboardingContract.Event.OnNextClicked(pagerState.currentPage))
                    performClickHapticFeedback(hapticFeedback)
                }
            }
        }
    }
}
