package dev.kigya.mindplex.feature.game.presentation.block

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.common.extension.toPx
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerBackgroundArc
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerCounter
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerProgressArc
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerText

private const val TOTAL_ANGLE = 360f
private const val START_ANGLE = -90f

@Composable
internal fun GameTimer(
    state: GameContract.State,
    modifier: Modifier = Modifier,
) {
    MindplexMeasurablePlaceholder(state.isLoading) {
        val progress = remember(state.currentTime) {
            state.currentTime / GameContract.TIME_LIMIT.toFloat()
        }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(),
        )

        BoxWithConstraints(
            modifier = modifier.padding(GameTheme.dimension.dp36.value),
            contentAlignment = Alignment.Center,
        ) {
            val textSize = GameTheme.typography.gameTimerCounter.value.fontSize.toPx()
            val circleSize = textSize.dp + GameTheme.dimension.dp36.value * 2

            Box(
                modifier = Modifier.size(circleSize),
                contentAlignment = Alignment.Center,
            ) {
                val backgroundColor = GameTheme.colorScheme.gameTimerBackgroundArc
                val progressColor = GameTheme.colorScheme.gameTimerProgressArc

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val totalAngle = TOTAL_ANGLE
                    val sweepAngle = totalAngle * animatedProgress

                    drawArc(
                        color = backgroundColor.value,
                        startAngle = START_ANGLE,
                        sweepAngle = totalAngle,
                        useCenter = true,
                    )

                    drawArc(
                        color = progressColor.value,
                        startAngle = START_ANGLE,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                    )
                }

                MindplexText(
                    value = state.currentTime.toString(),
                    typography = GameTheme.typography.gameTimerCounter,
                    color = GameTheme.colorScheme.gameTimerText,
                )
            }
        }
    }
}
