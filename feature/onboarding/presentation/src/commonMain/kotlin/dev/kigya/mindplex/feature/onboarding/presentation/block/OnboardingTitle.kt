package dev.kigya.mindplex.feature.onboarding.presentation.block

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.FADE_ANIMATION_DURATION_MILLIS
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingTitle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OnboardingTitle(
    state: OnboardingContract.State,
    page: Int,
) {
    val alpha by animateFloatAsState(
        targetValue = if (state.shouldDisplayTitle) 1f else 0f,
        animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS),
    )

    state.onboardingData[page].titleTextResource?.let { titleResource ->
        MindplexText(
            value = stringResource(titleResource),
            color = OnboardingTheme.colorScheme.onboardingTitle,
            typography = OnboardingTheme.typography.onboardingTitle,
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                }
                .fillMaxWidth()
                .padding(horizontal = OnboardingTheme.dimension.dp24.value)
                .testTag("onboarding_title_$page"),
        )
    }
}
