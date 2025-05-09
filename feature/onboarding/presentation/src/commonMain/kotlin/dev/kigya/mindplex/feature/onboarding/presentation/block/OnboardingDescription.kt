package dev.kigya.mindplex.feature.onboarding.presentation.block

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingDescription
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OnboardingDescription(
    state: OnboardingContract.State,
    page: Int,
) {
    val alpha by animateFloatAsState(
        targetValue = if (state.shouldDisplayTitle) 1f else 0f,
        animationSpec = tween(durationMillis = FADE_ANIMATION_DURATION_MILLIS),
    )

    state.onboardingData[page].descriptionTextResource?.let { descriptionResource ->
        MindplexText(
            value = stringResource(descriptionResource),
            color = OnboardingTheme.colorScheme.onboardingDescription,
            typography = OnboardingTheme.typography.onboardingDescription,
            modifier = Modifier
                .height(OnboardingTheme.dimension.dp64.value)
                .graphicsLayer {
                    this.alpha = alpha
                }
                .fillMaxWidth()
                .padding(horizontal = OnboardingTheme.dimension.dp24.value)
                .testTag("onboarding_description_$page"),
            minLines = 2,
        )
    }
}
