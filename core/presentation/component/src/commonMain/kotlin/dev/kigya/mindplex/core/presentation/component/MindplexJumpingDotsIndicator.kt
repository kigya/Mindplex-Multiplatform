package dev.kigya.mindplex.core.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.common.extension.jumpingDotTransition
import dev.kigya.mindplex.core.presentation.component.theme.componentJumpingDotsIndicator
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme

@Composable
fun MindplexJumpingDotsIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    selectedColor: Color = MindplexTheme.colorScheme.componentJumpingDotsIndicator,
    unselectedColor: Color = MindplexTheme.colorScheme.componentJumpingDotsIndicator,
    itemWidth: Dp = MindplexTheme.dimension.dp8,
    itemHeight: Dp = MindplexTheme.dimension.dp8,
    itemRadius: Dp = MindplexTheme.dimension.dp8,
    itemSpacing: Dp = MindplexTheme.dimension.dp24,
    strokeWidth: Dp = MindplexTheme.dimension.dp1,
) = with(LocalDensity.current) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(pagerState.pageCount) { position ->
                val head = position * (itemWidth + itemSpacing).toPx()
                val tail = head + itemWidth.toPx()

                Canvas(modifier = Modifier.height(itemHeight)) {
                    val rect =
                        RoundRect(
                            left = head,
                            top = 0f,
                            right = tail,
                            bottom = itemHeight.toPx(),
                            cornerRadius = CornerRadius(itemRadius.toPx()),
                        )
                    val path = Path().apply { addRoundRect(rect) }
                    drawPath(
                        path = path,
                        color = unselectedColor,
                        style = Stroke(width = strokeWidth.toPx()),
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(itemWidth)
                .height(itemHeight)
                .jumpingDotTransition(
                    distance = (itemWidth + itemSpacing).toPx(),
                    currentPage = pagerState.currentPage,
                    currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
                    jumpScale = 0.5f,
                )
                .background(
                    color = selectedColor,
                    shape = CircleShape,
                ),
        )
    }
}
