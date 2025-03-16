@file:Suppress("MagicNumber")

package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme

@Immutable
object MindplexDotIndicatorDefaults {
    @Immutable
    data object Size {
        val circleSpacing: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp16

        val circleSize: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp8

        val innerCircle: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp8

        val layoutHeight: MindplexDsToken<Dp>
            @Composable
            get() = UiKitTheme.dimension.dp64
    }
}

/**
 * [Figma](https://figmashort.link/AZMEtm)
 */
@Composable
fun MindplexDotsIndicator(
    pagerState: PagerState,
    orientation: Orientation,
    color: MindplexDsToken<Color>,
    modifier: Modifier = Modifier,
) {
    val circleSpacing = MindplexDotIndicatorDefaults.Size.circleSpacing.value
    val circleSize = MindplexDotIndicatorDefaults.Size.circleSize.value
    val innerCircleSize = MindplexDotIndicatorDefaults.Size.innerCircle.value
    val layoutHeight = MindplexDotIndicatorDefaults.Size.layoutHeight.value

    val animatedValues = List(pagerState.pageCount) { page ->
        val isActive = page == pagerState.currentPage
        val animatedAlpha by animateFloatAsState(
            targetValue = if (isActive) 1f else 0.8f,
            animationSpec = tween(),
        )
        val animatedScale by animateFloatAsState(
            targetValue = if (isActive) 1.2f else 1f,
            animationSpec = tween(),
        )
        Pair(animatedAlpha, animatedScale)
    }

    Box(
        modifier = if (orientation == Orientation.Horizontal) {
            Modifier
                .fillMaxWidth()
                .height(layoutHeight)
                .then(modifier)
        } else {
            Modifier
                .fillMaxHeight()
                .width(layoutHeight)
                .then(modifier)
        },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier) {
            val distance = (circleSize + circleSpacing).toPx()

            val w = size.width
            val h = size.height

            val centerX = w / 2
            val centerY = h / 2

            if (orientation == Orientation.Horizontal) {
                val totalWidth = (pagerState.pageCount - 1) * distance + circleSize.toPx()
                val startX = centerX - (totalWidth / 2)

                repeat(pagerState.pageCount) { page ->
                    val (animatedAlpha, animatedScale) = animatedValues[page]

                    val x = startX + page * distance
                    val circleCenter = Offset(x, centerY)
                    val radius = circleSize.toPx() / 2 * animatedScale
                    val innerRadius = innerCircleSize.toPx() / 2 * animatedScale

                    drawCircle(
                        color = color.value,
                        center = circleCenter,
                        radius = radius,
                        style = Stroke(width = 2.dp.toPx()),
                        alpha = animatedAlpha,
                    )

                    if (page == pagerState.currentPage) {
                        drawCircle(
                            color = color.value,
                            center = circleCenter,
                            radius = innerRadius,
                            alpha = animatedAlpha,
                        )
                    }
                }
            } else {
                val totalHeight = (pagerState.pageCount - 1) * distance + circleSize.toPx()
                val startY = centerY - (totalHeight / 2)

                repeat(pagerState.pageCount) { page ->
                    val (animatedAlpha, animatedScale) = animatedValues[page]

                    val y = startY + page * distance
                    val circleCenter = Offset(centerX, y)
                    val radius = circleSize.toPx() / 2 * animatedScale
                    val innerRadius = innerCircleSize.toPx() / 2 * animatedScale

                    drawCircle(
                        color = color.value,
                        center = circleCenter,
                        radius = radius,
                        style = Stroke(width = 2.dp.toPx()),
                        alpha = animatedAlpha,
                    )

                    if (page == pagerState.currentPage) {
                        drawCircle(
                            color = color.value,
                            center = circleCenter,
                            radius = innerRadius,
                            alpha = animatedAlpha,
                        )
                    }
                }
            }
        }
    }
}
