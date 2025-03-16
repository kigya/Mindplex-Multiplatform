package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme

private const val BACKGROUND_SHADOW_ALPHA = .56f
private const val SCALE_IN_INITIAL_STATE = .8f
private const val TARGET_SCALE_OUT = .95f
private const val FULL_HEIGHT_RATIO = 8

@Composable
fun MindplexDialog(
    modifier: Modifier = Modifier,
    shouldShowDialog: Boolean,
    backgroundColor: MindplexDsToken<Color>,
    onDismissRequest: () -> Unit = { },
    content: @Composable () -> Unit = { },
) {
    var shouldShowAnimatedDialog by remember { mutableStateOf(false) }

    LaunchedEffect(shouldShowDialog) {
        if (shouldShowDialog) shouldShowAnimatedDialog = true
    }

    if (shouldShowAnimatedDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                var shouldAnimateIn by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) { shouldAnimateIn = true }

                AnimatedVisibility(
                    visible = shouldAnimateIn && shouldShowDialog,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    onDismissRequest()
                                }
                            }
                            .background(Color.Black.copy(alpha = BACKGROUND_SHADOW_ALPHA))
                            .fillMaxSize(),
                    )
                }
                AnimatedVisibility(
                    visible = shouldAnimateIn && shouldShowDialog,
                    enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(
                        initialScale = SCALE_IN_INITIAL_STATE,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMediumLow,
                        ),
                    ),
                    exit = slideOutVertically { it / FULL_HEIGHT_RATIO } +
                        fadeOut() + scaleOut(targetScale = TARGET_SCALE_OUT),
                ) {
                    Box(
                        Modifier
                            .pointerInput(Unit) { detectTapGestures { } }
                            .widthIn(min = 320.dp, max = 520.dp)
                            .shadow(
                                elevation = UiKitTheme.dimension.dp8.value,
                                shape = UiKitTheme.shape.rounding16.value,
                            )
                            .then(modifier)
                            .clip(UiKitTheme.shape.rounding16.value)
                            .background(backgroundColor.value),
                        contentAlignment = Alignment.Center,
                    ) { content() }

                    DisposableEffect(Unit) {
                        onDispose {
                            shouldShowAnimatedDialog = false
                        }
                    }
                }
            }
        }
    }
}
