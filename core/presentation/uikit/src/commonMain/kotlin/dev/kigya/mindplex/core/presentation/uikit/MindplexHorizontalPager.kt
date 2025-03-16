package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.core.spring
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme

@Composable
fun MindplexHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageSpacing: MindplexDsToken<Dp> = UiKitTheme.dimension.dp0,
    beyondViewportPageCount: Int = PagerDefaults.BeyondViewportPageCount,
    isUserScrollEnabled: Boolean = true,
    pageContent: @Composable PagerScope.(page: Int) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapAnimationSpec = spring(dampingRatio = 1.9f, stiffness = 600f),
        ),
        pageContent = pageContent,
        userScrollEnabled = isUserScrollEnabled,
        pageSpacing = pageSpacing.value,
        beyondViewportPageCount = beyondViewportPageCount,
    )
}
