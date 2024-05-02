package dev.kigya.mindplex.core.util.extension.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.absoluteValue

fun Modifier.jumpingDotTransition(
    distance: Float,
    currentPage: Int,
    currentPageOffsetFraction: Float,
    jumpScale: Float,
) = graphicsLayer {
    val scrollPosition = currentPage + currentPageOffsetFraction
    translationX = scrollPosition * distance

    val scale: Float
    val targetScale = jumpScale - 1f

    scale = if (currentPageOffsetFraction.absoluteValue < .5) {
        1.0f + (currentPageOffsetFraction.absoluteValue * 2) * targetScale;
    } else {
        jumpScale + ((1 - (currentPageOffsetFraction.absoluteValue * 2)) * targetScale);
    }

    scaleX = scale
    scaleY = scale
}
