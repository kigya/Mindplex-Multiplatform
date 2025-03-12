package dev.kigya.mindplex.core.presentation.common.extension

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

private const val SHIFT_CLICK_TARGET_TRANSLATION = 20f
private const val SHIFT_CLICK_DEFAULT_TRANSLATION = 0f

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
                shiftClickButtonState =
                    if (shiftClickButtonState == ShiftClickButtonState.Pressed) {
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

data class ShakeConfig(
    val iterations: Int,
    val intensity: Float = 100_000f,
    val rotate: Float = 0f,
    val rotateX: Float = 0f,
    val rotateY: Float = 0f,
    val scaleX: Float = 0f,
    val scaleY: Float = 0f,
    val translateX: Float = 0f,
    val translateY: Float = 0f,
)

@Composable
fun rememberShakeController(): ShakeController = remember { ShakeController() }

class ShakeController {
    var shakeConfig: ShakeConfig? by mutableStateOf(null)
        private set

    fun shake(shakeConfig: ShakeConfig) {
        this.shakeConfig = shakeConfig
    }

    fun reset() {
        shakeConfig = null
    }
}

fun Modifier.shake(shakeController: ShakeController) = composed {
    shakeController.shakeConfig?.let { shakeConfig ->
        val shake = remember { Animatable(0f) }

        LaunchedEffect(shakeController.shakeConfig) {
            val config = shakeController.shakeConfig ?: return@LaunchedEffect
            for (i in 0..config.iterations) {
                if (i % 2 == 0) {
                    shake.animateTo(1f, spring(stiffness = config.intensity))
                } else {
                    shake.animateTo(-1f, spring(stiffness = config.intensity))
                }
            }
            shake.animateTo(0f)
            shakeController.reset()
        }

        this
            .rotate(shake.value * shakeConfig.rotate)
            .graphicsLayer {
                rotationX = shake.value * shakeConfig.rotateX
                rotationY = shake.value * shakeConfig.rotateY
            }
            .scale(
                scaleX = 1f + (shake.value * shakeConfig.scaleX),
                scaleY = 1f + (shake.value * shakeConfig.scaleY),
            )
            .offset {
                IntOffset(
                    (shake.value * shakeConfig.translateX).roundToInt(),
                    (shake.value * shakeConfig.translateY).roundToInt(),
                )
            }
    } ?: this
}
