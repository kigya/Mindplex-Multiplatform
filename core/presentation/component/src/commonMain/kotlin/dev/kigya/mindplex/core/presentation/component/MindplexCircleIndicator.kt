package dev.kigya.mindplex.core.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.lerp
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme
import dev.kigya.mindplex.core.presentation.component.theme.ComponentTheme.componentCircleIndicator
import kotlin.math.absoluteValue

private const val PAGE_STROKE_OFFSET = 0.7f

@Composable
fun MindplexCircleIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ComponentTheme.dimension.dp12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until pagerState.pageCount) {
            val offset = pagerState.getIndicatorOffsetForPage(i)

            Box(
                modifier = Modifier.size(ComponentTheme.dimension.dp16),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    Modifier
                        .size(
                            lerp(
                                start = ComponentTheme.dimension.dp8,
                                stop = ComponentTheme.dimension.dp12,
                                fraction = offset,
                            ),
                        )
                        .border(
                            width = if (offset > PAGE_STROKE_OFFSET) {
                                ComponentTheme.dimension.dp2
                            } else {
                                ComponentTheme.dimension.dp1
                            },
                            color = ComponentTheme.colorScheme.componentCircleIndicator,
                            shape = CircleShape,
                        ),
                )
            }
        }
    }
}

private fun PagerState.getOffsetForPage(page: Int) = currentPage - page + currentPageOffsetFraction

private fun PagerState.getIndicatorOffsetForPage(page: Int) =
    1f - getOffsetForPage(page).coerceIn(-1f, 1f).absoluteValue
