package dev.kigya.mindplex.feature.onboarding.presentation.block

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingDescription
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OnboardingDescription(
    state: OnboardingContract.State,
    page: Int,
) = AnimatedVisibility(
    visible = state.shouldDisplayDescription,
    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
) {
    state.onboardingData[page].descriptionTextResource?.let { descriptionResource ->
        MindplexText(
            value = stringResource(descriptionResource),
            color = OnboardingTheme.colorScheme.onboardingDescription,
            typography = OnboardingTheme.typography.onboardingDescription,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = OnboardingTheme.dimension.dp24.value)
                .testTag("onboarding_description_$page"),
            minLines = 2,
        )
    }
}
