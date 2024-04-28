package dev.kigya.mindplex.core.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import dev.kigya.mindplex.core.util.compose.LaunchedEffectSaveable
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition

@Composable
fun MindplexLottie(
    reader: suspend () -> ByteArray,
    modifier: Modifier = Modifier,
    isRestartable: Boolean = false,
    shouldBeReversedOnRepeat: Boolean = false,
    speed: Float = 1f,
    onFinish: () -> Unit = {},
) {
    var json by remember { mutableStateOf<String?>(null) }
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(json.orEmpty()))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        restartOnPlay = isRestartable,
        reverseOnRepeat = shouldBeReversedOnRepeat,
        speed = speed,
    )
    val isAnimationComplete by derivedStateOf { progress == 1f }

    LaunchedEffect(Unit) { json = reader().decodeToString() }
    LaunchedEffect(isAnimationComplete) { if (isAnimationComplete) onFinish() }

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        contentScale = ContentScale.FillBounds,
        progress = { progress },
    )
}
