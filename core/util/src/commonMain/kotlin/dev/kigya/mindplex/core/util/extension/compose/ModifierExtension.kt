package dev.kigya.mindplex.core.util.extension.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.absoluteValue

private const val HALF_POINT = 0.5f
private const val DOUBLE = 2.0f
private const val ONE_F = 1.0f

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

    val targetScale = jumpScale - ONE_F
    val scale: Float = if (currentPageOffsetFraction.absoluteValue < HALF_POINT) {
        ONE_F + currentPageOffsetFraction.absoluteValue * DOUBLE * targetScale
    } else {
        jumpScale + (ONE_F - (currentPageOffsetFraction.absoluteValue * DOUBLE)) * targetScale
    }

    scaleX = scale
    scaleY = scale
}
