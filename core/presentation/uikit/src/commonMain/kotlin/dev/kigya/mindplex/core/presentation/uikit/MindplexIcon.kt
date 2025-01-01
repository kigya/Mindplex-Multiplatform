package dev.kigya.mindplex.core.presentation.uikit

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
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * [Figma](https://figmashort.link/azBnxZ)
 */
@Composable
fun MindplexIcon(
    resource: DrawableResource,
    modifier: Modifier = Modifier,
    color: MindplexDsToken<Color>? = null,
    scale: (() -> Float)? = null,
) = Image(
    painter = painterResource(resource),
    contentDescription = null,
    scale?.let {
        val animatedScale by animateFloatAsState(
            targetValue = it(),
            animationSpec = tween(easing = FastOutSlowInEasing),
        )
        modifier.scale(animatedScale)
    } ?: modifier,
    colorFilter = color?.let { ColorFilter.tint(it.value) },
)
