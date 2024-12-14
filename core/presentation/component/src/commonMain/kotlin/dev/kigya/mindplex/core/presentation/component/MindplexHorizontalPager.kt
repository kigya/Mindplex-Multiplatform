package dev.kigya.mindplex.core.presentation.component

import androidx.compose.animation.core.spring
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme

@Composable
fun MindplexHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageSpacing: Dp = ComponentTheme.dimension.dp0,
    beyondViewportPageCount: Int = PagerDefaults.BeyondViewportPageCount,
    pageContent: @Composable PagerScope.(Int) -> Unit,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapAnimationSpec = spring(dampingRatio = 1.9f, stiffness = 600f),
        ),
        pageContent = pageContent,
        pageSpacing = pageSpacing,
        beyondViewportPageCount = beyondViewportPageCount,
    )
}
