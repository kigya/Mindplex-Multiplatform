package dev.kigya.mindplex.feature.game.presentation.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.common.extension.toPx
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerBackgroundArc
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerCounter
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerProgressArc
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTimerText

private const val TOTAL_ANGLE = 360f
private const val START_ANGLE = -90f

@Composable
internal fun GameTimer(
    modifier: Modifier = Modifier,
    remainingTime: Int,
    initialTime: Int,
) {
    val progress = remainingTime / initialTime.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(),
    )

    BoxWithConstraints(
        modifier = modifier.padding(GameTheme.dimension.dp36),
        contentAlignment = Alignment.Center,
    ) {
        val textSize = GameTheme.typography.gameTimerCounter.fontSize.toPx()
        val circleSize = textSize.dp + GameTheme.dimension.dp36 * 2

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
                    color = backgroundColor,
                    startAngle = START_ANGLE,
                    sweepAngle = totalAngle,
                    useCenter = true,
                )

                drawArc(
                    color = progressColor,
                    startAngle = START_ANGLE,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                )
            }

            MindplexText(
                text = remainingTime.toString(),
                style = GameTheme.typography.gameTimerCounter,
                color = GameTheme.colorScheme.gameTimerText,
            )
        }
    }
}
