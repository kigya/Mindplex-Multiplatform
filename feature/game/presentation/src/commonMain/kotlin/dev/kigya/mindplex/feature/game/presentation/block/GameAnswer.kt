package dev.kigya.mindplex.feature.game.presentation.block

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.kigya.mindplex.core.presentation.common.extension.ShakeConfig
import dev.kigya.mindplex.core.presentation.common.extension.rememberShakeController
import dev.kigya.mindplex.core.presentation.common.extension.shake
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.MindplexButton
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme

internal enum class ShakeEvent {
    POSITIVE,
    NEGATIVE,
    NONE,
}

@Composable
@Suppress("MagicNumber")
internal fun GameAnswer(
    text: String,
    backgroundColor: MindplexDsToken<Color>,
    borderColor: MindplexDsToken<Color>,
    textColor: MindplexDsToken<Color>,
    shakeEvent: ShakeEvent = ShakeEvent.NONE,
    isValidating: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val shakeController = rememberShakeController()

    val negativeShake = remember {
        ShakeConfig(
            iterations = 4,
            intensity = 2000f,
            rotateY = 2f,
            translateX = 4f,
        )
    }

    val positiveShake = remember {
        ShakeConfig(
            iterations = 4,
            intensity = 2000f,
            rotateX = -2f,
            translateY = 4f,
        )
    }

    LaunchedEffect(shakeEvent, text) {
        when (shakeEvent) {
            ShakeEvent.POSITIVE -> shakeController.shake(positiveShake)
            ShakeEvent.NEGATIVE -> shakeController.shake(negativeShake)
            ShakeEvent.NONE -> Unit
        }
    }

    MindplexButton(
        text = text,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        textColor = textColor,
        modifier = modifier
            .padding(GameTheme.dimension.dp8.value)
            .shake(shakeController),
        onClick = {
            if (!isValidating) onClick()
        },
    )
}
