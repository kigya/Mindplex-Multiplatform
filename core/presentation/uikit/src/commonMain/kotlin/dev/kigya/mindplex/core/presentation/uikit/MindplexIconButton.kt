package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import org.jetbrains.compose.resources.DrawableResource

/**
 * [Figma](https://figmashort.link/WCm57K)
 */
@Composable
fun MindplexIconButton(
    resource: DrawableResource,
    modifier: Modifier = Modifier,
    color: MindplexDsToken<Color>? = null,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        MindplexIcon(
            resource = resource,
            color = color,
        )
    }
}
