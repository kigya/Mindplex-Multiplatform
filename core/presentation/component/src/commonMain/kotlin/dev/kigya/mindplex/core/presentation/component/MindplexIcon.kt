package dev.kigya.mindplex.core.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import dev.kigya.mindplex.core.util.extension.empty
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MindplexIcon(
    modifier: Modifier = Modifier,
    drawableResource: DrawableResource,
    tintColor: Color? = null,
    contentDescription: String = String.empty,
) {
    Image(
        modifier = modifier,
        painter = painterResource(drawableResource),
        contentDescription = contentDescription,
        colorFilter = tintColor?.let(ColorFilter.Companion::tint),
    )
}

@Composable
fun MindplexScaleIcon(
    modifier: Modifier = Modifier,
    scale: Float,
    drawableResource: DrawableResource,
    tintColor: Color? = null,
    contentDescription: String = String.empty,
) {
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(easing = FastOutSlowInEasing),
    )

    Image(
        modifier = modifier.scale(animatedScale),
        painter = painterResource(drawableResource),
        contentDescription = contentDescription,
        colorFilter = tintColor?.let(ColorFilter.Companion::tint),
    )
}
