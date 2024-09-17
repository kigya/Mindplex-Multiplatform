package dev.kigya.mindplex.core.presentation.common.extension

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.absoluteValue

private const val DOUBLE_JUMPING_SCALE = 2.0f
private const val DEFAULT_JUMPING_SCALE = 1.0f
private const val SHIFT_CLICK_TARGET_TRANSLATION = 20f
private const val SHIFT_CLICK_DEFAULT_TRANSLATION = 0f

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

    val targetScale = jumpScale - DEFAULT_JUMPING_SCALE
    val currentPageOffsetFractionAbs = currentPageOffsetFraction.absoluteValue * DOUBLE_JUMPING_SCALE
    val scale = if (currentPageOffsetFractionAbs < DEFAULT_JUMPING_SCALE) {
        DEFAULT_JUMPING_SCALE + currentPageOffsetFractionAbs * targetScale
    } else {
        jumpScale + (DEFAULT_JUMPING_SCALE - currentPageOffsetFractionAbs) * targetScale
    }

    scaleX = scale
    scaleY = scale
}

enum class ShiftClickButtonState { Pressed, Idle }

fun Modifier.shiftClickEffect(
    onChangeState: (ShiftClickButtonState) -> Unit = { },
    onClick: () -> Unit,
) = composed {
    var shiftClickButtonState by remember { mutableStateOf(ShiftClickButtonState.Idle) }
    val tx by animateFloatAsState(
        if (shiftClickButtonState == ShiftClickButtonState.Pressed) {
            SHIFT_CLICK_TARGET_TRANSLATION
        } else {
            SHIFT_CLICK_DEFAULT_TRANSLATION
        },
    )

    this
        .graphicsLayer { translationX = tx }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onClick() },
        )
        .pointerInput(shiftClickButtonState) {
            awaitPointerEventScope {
                shiftClickButtonState = if (shiftClickButtonState == ShiftClickButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ShiftClickButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ShiftClickButtonState.Pressed
                }
                onChangeState(shiftClickButtonState)
            }
        }
}
