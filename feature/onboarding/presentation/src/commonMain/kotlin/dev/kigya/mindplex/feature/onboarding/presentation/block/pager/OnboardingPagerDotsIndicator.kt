@file:Suppress("MagicNumber")

package dev.kigya.mindplex.feature.onboarding.presentation.block.pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.uikit.MindplexDotsIndicator
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.FADE_ANIMATION_DURATION_MILLIS
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingDotsIndicator

private const val ANIMATION_SCALE = 0.8f

@Composable
internal fun RowScope.OnboardingPagerDotsIndicator(
    state: OnboardingContract.State,
    orientation: Orientation,
    pagerState: PagerState,
) = drawOnboardingPagerDots(
    orientation = orientation,
    state = state,
    pagerState = pagerState,
    modifier = Modifier.weight(0.1f),
)

@Composable
internal fun ColumnScope.OnboardingPagerDotsIndicator(
    state: OnboardingContract.State,
    orientation: Orientation,
    pagerState: PagerState,
) = drawOnboardingPagerDots(
    orientation = orientation,
    state = state,
    pagerState = pagerState,
    modifier = Modifier.fillMaxWidth(),
)

@Composable
private fun drawOnboardingPagerDots(
    orientation: Orientation,
    state: OnboardingContract.State,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val testTagModifier = modifier.testTag("onboarding_dots_indicator")

    if (orientation == Orientation.Horizontal) {
        AnimatedVisibility(
            visible = state.shouldDisplayDotsIndicator,
            enter = scaleIn(
                initialScale = ANIMATION_SCALE,
                transformOrigin = TransformOrigin.Center,
                animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS),
            ) + fadeIn(animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS)),
            exit = scaleOut(
                targetScale = ANIMATION_SCALE,
                transformOrigin = TransformOrigin.Center,
                animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS),
            ) + fadeOut(animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS)),
        ) {
            Row(
                modifier = testTagModifier,
                horizontalArrangement = Arrangement.Center,
            ) {
                MindplexDotsIndicator(
                    pagerState = pagerState,
                    orientation = Orientation.Horizontal,
                    color = OnboardingTheme.colorScheme.onboardingDotsIndicator,
                )
                MindplexSpacer(size = OnboardingTheme.dimension.dp64)
            }
        }
    } else {
        val alpha by animateFloatAsState(
            targetValue = if (state.shouldDisplayDotsIndicator) 1f else 0f,
            animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS),
        )
        val scale by animateFloatAsState(
            targetValue = if (state.shouldDisplayDotsIndicator) 1f else ANIMATION_SCALE,
            animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS),
        )

        Box(
            modifier = Modifier.graphicsLayer {
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
            },
        ) {
            MindplexDotsIndicator(
                pagerState = pagerState,
                orientation = Orientation.Vertical,
                color = OnboardingTheme.colorScheme.onboardingDotsIndicator,
                modifier = testTagModifier,
            )
        }
    }
}
