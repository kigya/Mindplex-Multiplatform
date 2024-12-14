package dev.kigya.mindplex.feature.onboarding.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.component.MindplexJumpingDotsIndicator
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme

@Composable
internal fun ColumnScope.OnboardingPagerDotsIndicator(
    state: OnboardingContract.State,
    pagerState: PagerState,
) {
    MindplexSpacer(size = OnboardingTheme.dimension.dp24)
    AnimatedVisibility(visible = state.shouldDisplayDotsIndicator) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("onboarding_dots_indicator"),
            horizontalArrangement = Arrangement.Center,
        ) {
            MindplexJumpingDotsIndicator(pagerState = pagerState)
            MindplexSpacer(size = OnboardingTheme.dimension.dp64)
        }
    }
}
