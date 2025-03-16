package dev.kigya.mindplex.feature.onboarding.presentation.block.pager

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.uikit.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.uikit.MindplexVerticalPager

@Composable
internal fun ColumnScope.OnboardingPager(
    pagerState: PagerState,
    orientation: Orientation,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) = drawOnboardingPager(
    pagerState = pagerState,
    orientation = orientation,
    pageContent = pageContent,
    modifier = Modifier.fillMaxWidth(),
)

@Composable
internal fun RowScope.OnboardingPager(
    pagerState: PagerState,
    orientation: Orientation,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) = drawOnboardingPager(
    pagerState = pagerState,
    orientation = orientation,
    pageContent = pageContent,
    modifier = Modifier
        .weight(0.5f)
        .fillMaxHeight(),
)

@Composable
private fun drawOnboardingPager(
    pagerState: PagerState,
    orientation: Orientation,
    modifier: Modifier = Modifier,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) = if (orientation == Orientation.Horizontal) {
    val testTagModifier = modifier.testTag("onboarding_pager")

    MindplexHorizontalPager(
        pagerState = pagerState,
        modifier = testTagModifier,
        pageContent = pageContent,
    )
} else {
    MindplexVerticalPager(
        pagerState = pagerState,
        modifier = modifier,
        pageContent = pageContent,
    )
}
