@file:Suppress("MagicNumber")

package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.common.extension.by
import dev.kigya.mindplex.core.presentation.common.util.lerp
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentNavigationBarBackground
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentNavigationBarBall

private const val BALL_TRANSFORM_PIVOT_FRACTION_X = 0.5f
private const val BALL_TRANSFORM_PIVOT_FRACTION_Y = 0f

@Composable
fun AnimatedNavigationBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    backgroundColor: MindplexDsToken<Color> = UiKitTheme.colorScheme.componentNavigationBarBackground,
    ballColor: MindplexDsToken<Color> = UiKitTheme.colorScheme.componentNavigationBarBall,
    cornerRadius: MindplexDsToken<Dp> = UiKitTheme.dimension.dp64,
    ballSize: MindplexDsToken<Dp> = UiKitTheme.dimension.dp8,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current

    val ballAnimation = remember { Parabolic(ballSize = ballSize.value) }
    val indentAnimation = remember { HeightIndentAnimation(ballSize = ballSize.value) }

    var itemPositions by remember { mutableStateOf(listOf<Offset>()) }
    val measurePolicy = animatedNavBarMeasurePolicy {
        itemPositions = it.map { xCoordinate -> Offset(xCoordinate, 0f) }
    }
    val selectedItemOffset by remember(selectedIndex, itemPositions) {
        derivedStateOf { if (itemPositions.isNotEmpty()) itemPositions[selectedIndex] else Offset.Unspecified }
    }
    val indentShape = indentAnimation.animateIndentShapeAsState(
        shapeCornerRadius = cornerRadius.value by density,
        targetOffset = selectedItemOffset,
    )
    val ballAnimInfoState = ballAnimation.animateAsState(
        targetOffset = selectedItemOffset,
    )

    Box(modifier = modifier) {
        Layout(
            modifier = Modifier
                .graphicsLayer {
                    clip = true
                    shape = indentShape.value
                }
                .background(backgroundColor.value),
            content = content,
            measurePolicy = measurePolicy,
        )

        if (ballAnimInfoState.value.offset.isSpecified) {
            ColorBall(
                ballAnimInfo = ballAnimInfoState.value,
                ballColor = ballColor.value,
                sizeDp = ballSize.value,
            )
        }
    }
}

@Composable
private fun ColorBall(
    modifier: Modifier = Modifier,
    ballColor: Color,
    ballAnimInfo: BallAnimInfo,
    sizeDp: Dp,
) {
    Box(
        modifier = modifier
            .ballTransform(ballAnimInfo)
            .size(sizeDp)
            .clip(CircleShape)
            .background(ballColor),
    )
}

@Stable
private class HeightIndentAnimation(
    private val animationSpec: FiniteAnimationSpec<Float> = tween(),
    private val indentWidth: Dp = 64.dp,
    private val indentHeight: Dp = 12.dp,
    private val ballSize: Dp,
) : IndentAnimation {

    @Composable
    override fun animateIndentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: Float,
    ): State<Shape> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(IndentRectShape(IndentShapeData())) }
        }

        val fraction = remember { Animatable(0f) }
        var to by remember { mutableStateOf(Offset.Zero) }
        var from by remember { mutableStateOf(Offset.Zero) }

        val density = LocalDensity.current

        suspend fun setNewAnimationPoints() {
            from = to
            to = targetOffset
            fraction.snapTo(0f)
        }

        suspend fun changeToAndFromPointsWhileAnimating() {
            from = to
            to = targetOffset
            fraction.snapTo(2f - fraction.value)
        }

        fun changeToAnimationPointWhileAnimating() {
            to = targetOffset
        }

        LaunchedEffect(targetOffset) {
            when {
                isNotRunning(fraction.value) -> setNewAnimationPoints()
                isExitIndentAnimating(fraction.value) -> changeToAnimationPointWhileAnimating()
                isEnterIndentAnimating(fraction.value) -> changeToAndFromPointsWhileAnimating()
            }
            fraction.animateTo(2f, animationSpec)
        }

        return produceState(
            initialValue = IndentRectShape(
                indentShapeData = IndentShapeData(
                    ballOffset = (ballSize by density) / 2f,
                    width = indentWidth by density,
                ),
            ),
            key1 = fraction.value,
            key2 = shapeCornerRadius,
        ) {
            this.value = this.value.copy(
                yIndent = calculateYIndent(fraction.value, density),
                xIndent = if (fraction.value <= 1f) from.x else to.x,
                cornerRadius = shapeCornerRadius,
            )
        }
    }

    private fun calculateYIndent(
        fraction: Float,
        density: Density,
    ): Float = if (fraction <= 1f) {
        lerp(indentHeight.by(density), 0f, fraction)
    } else {
        lerp(0f, indentHeight.by(density), fraction - 1f)
    }

    private fun isExitIndentAnimating(fraction: Float) = fraction <= 1f
    private fun isEnterIndentAnimating(fraction: Float) = fraction > 1f
    private fun isNotRunning(fraction: Float) = fraction == 0f || fraction == 2f
}

interface IndentAnimation {
    @Composable
    fun animateIndentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: Float,
    ): State<Shape>
}

@Stable
private class Parabolic(
    private val animationSpec: FiniteAnimationSpec<Float> = tween(),
    private val maxHeight: Dp = 100.dp,
    private val ballSize: Dp,
) {
    @Composable
    fun animateAsState(targetOffset: Offset): State<BallAnimInfo> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(BallAnimInfo()) }
        }

        var from by remember { mutableStateOf(targetOffset) }
        var to by remember { mutableStateOf(targetOffset) }
        val fraction = remember { Animatable(0f) }

        val path = remember { Path() }
        val pathMeasurer = remember { PathMeasure() }
        val pathLength = remember { mutableStateOf(0f) }
        val pos = remember { floatArrayOf(Float.MAX_VALUE, Float.MAX_VALUE) }
        val tan = remember { floatArrayOf(0f, 0f) }

        val density = LocalDensity.current
        val maxHeightPx = remember(maxHeight) { with(density) { maxHeight.toPx() } }

        fun measurePosition() {
            measurePosition(pathMeasurer, pathLength, pos, tan, fraction)
        }

        LaunchedEffect(targetOffset) {
            var height = if (to != targetOffset) {
                maxHeightPx
            } else {
                START_MIN_HEIGHT
            }

            if (isNotRunning(fraction.value)) {
                from = to
                to = targetOffset
            } else {
                measurePosition()
                from = Offset(x = pos[0], y = pos[1])
                to = targetOffset
                height = maxHeightPx + pos[1]
            }

            path.createParabolaTrajectory(from = from, to = to, height = height)

            pathMeasurer.setPath(path, false)
            pathLength.value = pathMeasurer.length

            fraction.snapTo(0f)
            fraction.animateTo(1f, animationSpec)
        }

        val verticalOffset = remember { 2.dp by density }
        val ballSizePx = remember { ballSize by density }
        val ballPadding = UiKitTheme.dimension.dp8

        return produceState(
            initialValue = BallAnimInfo(),
            key1 = pos,
            key2 = ballSizePx,
            key3 = fraction.value,
        ) {
            measurePosition()
            if (pos[0] == Float.MAX_VALUE) {
                BallAnimInfo()
            } else {
                this.value = this.value.copy(
                    offset = calculateNewOffset(
                        pos,
                        ballSizePx,
                        verticalOffset,
                        padding = ballPadding.value by density,
                    ),
                )
            }
        }
    }

    private fun Path.createParabolaTrajectory(
        from: Offset,
        to: Offset,
        height: Float,
    ) {
        reset()
        moveTo(from.x, from.y)
        quadraticTo(
            x1 = (from.x + to.x) / 2f,
            y1 = from.y - height,
            x2 = to.x,
            y2 = to.y,
        )
    }

    private fun calculateNewOffset(
        pos: FloatArray,
        ballSizePx: Float,
        verticalOffset: Float,
        padding: Float = 8f,
    ): Offset = Offset(
        x = pos[0] - (ballSizePx / 2),
        y = pos[1] - verticalOffset - padding,
    )

    private fun isNotRunning(fraction: Float) = fraction == 0f || fraction == 1f

    private fun measurePosition(
        pathMeasure: PathMeasure,
        pathLength: State<Float>,
        pos: FloatArray,
        tan: FloatArray,
        fraction: Animatable<Float, AnimationVector1D>,
    ) {
        val distance = pathLength.value * fraction.value

        val positionOffset = pathMeasure.getPosition(distance)
        if (positionOffset != Offset.Unspecified) {
            pos[0] = positionOffset.x
            pos[1] = positionOffset.y
        } else {
            pos[0] = Float.MAX_VALUE
            pos[1] = Float.MAX_VALUE
        }

        val tangentOffset = pathMeasure.getTangent(distance)
        if (tangentOffset != Offset.Unspecified) {
            tan[0] = tangentOffset.x
            tan[1] = tangentOffset.y
        } else {
            tan[0] = 0f
            tan[1] = 0f
        }
    }

    private companion object {
        const val START_MIN_HEIGHT = -50f
    }
}

@Composable
private fun animatedNavBarMeasurePolicy(onBallPositionsCalculated: (ArrayList<Float>) -> Unit) =
    remember {
        barMeasurePolicy(onBallPositionsCalculated = onBallPositionsCalculated)
    }

private fun barMeasurePolicy(onBallPositionsCalculated: (ArrayList<Float>) -> Unit) =
    MeasurePolicy { measurables, constraints ->

        check(measurables.isNotEmpty()) {
            "There must be at least one element"
        }
        val itemWidth = constraints.maxWidth / measurables.size
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxWidth = itemWidth))
        }

        val gap = calculateGap(placeables, constraints.maxWidth)
        val height = placeables.maxOf { it.height }

        layout(constraints.maxWidth, height) {
            var xPosition = gap

            val positions = arrayListOf<Float>()

            placeables.forEachIndexed { index, _ ->
                placeables[index].placeRelative(xPosition, 0)

                positions.add(
                    element = calculatePointPosition(
                        xPosition,
                        placeables[index].width,
                    ),
                )

                xPosition += placeables[index].width + gap
            }
            onBallPositionsCalculated(positions)
        }
    }

fun calculatePointPosition(
    xButtonPosition: Int,
    buttonWidth: Int,
): Float = xButtonPosition + (buttonWidth / 2f)

fun calculateGap(
    placeables: List<Placeable>,
    width: Int,
): Int {
    var allWidth = 0
    placeables.forEach { placeable ->
        allWidth += placeable.width
    }
    return (width - allWidth) / (placeables.size + 1)
}

private class IndentPath(
    private val rect: Rect,
) {
    private val maxX = 110f
    private val maxY = 34f

    private fun translate(
        x: Float,
        y: Float,
    ): PointF = PointF(
        (x / maxX * rect.width) + rect.left,
        (y / maxY * rect.height) + rect.top,
    )

    fun createPath(): Path {
        val start = translate(x = 0f, y = 0f)
        val middle = translate(x = 55f, y = 34f)
        val end = translate(x = 110f, y = 0f)

        val control1 = translate(x = 23f, y = 0f)
        val control2 = translate(x = 39f, y = 34f)
        val control3 = translate(x = 71f, y = 34f)
        val control4 = translate(x = 87f, y = 0f)

        val path = Path()
        path.moveTo(start.x, start.y)
        path.cubicTo(control1.x, control1.y, control2.x, control2.y, middle.x, middle.y)
        path.cubicTo(control3.x, control3.y, control4.x, control4.y, end.x, end.y)

        return path
    }
}

private data class PointF(
    val x: Float,
    val y: Float,
)

private class IndentRectShape(
    private val indentShapeData: IndentShapeData,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(
        Path().addRoundRectWithIndent(size, indentShapeData, layoutDirection),
    )

    fun copy(
        cornerRadius: Float = indentShapeData.cornerRadius,
        xIndent: Float = indentShapeData.xIndent,
        yIndent: Float = indentShapeData.height,
        ballSize: Float = indentShapeData.ballOffset,
    ) = IndentRectShape(
        indentShapeData.copy(
            cornerRadius = cornerRadius,
            xIndent = xIndent,
            height = yIndent,
            ballOffset = ballSize,
        ),
    )
}

private fun Path.addRoundRectWithIndent(
    size: Size,
    indentShapeData: IndentShapeData,
    layoutDirection: LayoutDirection,
): Path {
    val width = size.width
    val height = size.height
    val cornerRadius = indentShapeData.cornerRadius
    val sweepAngleDegrees = 90f

    return apply {
        moveTo(chooseCornerSize(size.height, cornerRadius), 0f)

        val xOffset =
            if (layoutDirection == LayoutDirection.Ltr) {
                indentShapeData.xIndent - indentShapeData.width / 2
            } else {
                size.width - indentShapeData.xIndent - indentShapeData.width / 2
            }

        if (xOffset > cornerRadius / 4) {
            addPath(
                IndentPath(
                    Rect(
                        Offset(
                            x = xOffset,
                            y = 0f,
                        ),
                        Size(indentShapeData.width, indentShapeData.height),
                    ),
                ).createPath(),
            )
        }

        lineTo(width - cornerRadius, 0f)
        arcTo(
            rect = Rect(
                offset = Offset(width - cornerRadius, 0f),
                size = Size(cornerRadius, cornerRadius),
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false,
        )
        lineTo(width, height - cornerRadius)
        arcTo(
            rect = Rect(
                offset = Offset(
                    width - cornerRadius,
                    height - cornerRadius,
                ),
                size = Size(cornerRadius, cornerRadius),
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false,
        )
        lineTo(width - cornerRadius, height)
        arcTo(
            rect = Rect(
                offset = Offset(x = 0f, y = height - cornerRadius),
                size = Size(cornerRadius, cornerRadius),
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false,
        )
        lineTo(0f, cornerRadius)
        arcTo(
            rect = Rect(
                offset = Offset(x = 0f, y = 0f),
                size = Size(cornerRadius, cornerRadius),
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false,
        )
        close()
    }
}

private fun chooseCornerSize(
    sizeHeight: Float,
    cornerRadius: Float,
): Float = if (sizeHeight > cornerRadius) {
    cornerRadius
} else {
    sizeHeight
}

private data class IndentShapeData(
    val xIndent: Float = 0f,
    val height: Float = 0f,
    val width: Float = 0f,
    val cornerRadius: Float = 16f,
    val ballOffset: Float = 0f,
)

private data class BallAnimInfo(
    val scale: Float = 1f,
    val offset: Offset = Offset.Unspecified,
)

private fun Modifier.ballTransform(ballAnimInfo: BallAnimInfo) = this
    .offset {
        IntOffset(
            x = ballAnimInfo.offset.x.toInt(),
            y = ballAnimInfo.offset.y.toInt(),
        )
    }
    .graphicsLayer {
        scaleY = ballAnimInfo.scale
        scaleX = ballAnimInfo.scale
        transformOrigin = TransformOrigin(
            pivotFractionX = BALL_TRANSFORM_PIVOT_FRACTION_X,
            pivotFractionY = BALL_TRANSFORM_PIVOT_FRACTION_Y,
        )
    }
