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
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingTitle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OnboardingTitle(
    state: OnboardingContract.State,
    page: Int,
) = AnimatedVisibility(
    visible = state.shouldDisplayTitle,
    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
) {
    state.onboardingData[page].titleTextResource?.let { titleResource ->
        MindplexText(
            value = stringResource(titleResource),
            color = OnboardingTheme.colorScheme.onboardingTitle,
            typography = OnboardingTheme.typography.onboardingTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = OnboardingTheme.dimension.dp24.value)
                .testTag("onboarding_title_$page"),
        )
    }
}
