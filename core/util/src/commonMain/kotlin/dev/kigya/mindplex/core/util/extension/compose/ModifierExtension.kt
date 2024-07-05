package dev.kigya.mindplex.core.util.extension.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.absoluteValue

private const val DOUBLE_SCALE = 2.0f
private const val DEFAULT_SCALE = 1.0f

/**
 * Adds a jumping dot transition effect to a modifier.
 * @param distance The distance each dot should move.
 * @param currentPage The current page index.
 * @param currentPageOffsetFraction The fraction of the page offset.
 * @param jumpScale The scale factor when the dot is at the peak of the jump.
 * @return The modified [Modifier] with the transition applied.
 */
fun Modifier.jumpingDotTransition(
    distance: Float,
    currentPage: Int,
    currentPageOffsetFraction: Float,
    jumpScale: Float,
) = graphicsLayer {
    val scrollPosition = currentPage + currentPageOffsetFraction
    translationX = scrollPosition * distance

    val targetScale = jumpScale - DEFAULT_SCALE
    val currentPageOffsetFractionAbs = currentPageOffsetFraction.absoluteValue * DOUBLE_SCALE
    val scale = if (currentPageOffsetFractionAbs < DEFAULT_SCALE) {
        DEFAULT_SCALE + currentPageOffsetFractionAbs * targetScale
    } else {
        jumpScale + (DEFAULT_SCALE - currentPageOffsetFractionAbs) * targetScale
    }

    scaleX = scale
    scaleY = scale
}
