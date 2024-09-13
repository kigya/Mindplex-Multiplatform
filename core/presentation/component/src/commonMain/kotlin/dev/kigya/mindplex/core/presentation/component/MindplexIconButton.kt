package dev.kigya.mindplex.core.presentation.component

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.kigya.mindplex.core.util.extension.empty
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun MindplexIconButton(
    modifier: Modifier = Modifier,
    drawableResource: DrawableResource,
    tintColor: Color? = null,
    contentDescription: String = String.empty,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        MindplexIcon(
            drawableResource = drawableResource,
            tintColor = tintColor,
            contentDescription = contentDescription,
        )
    }
}
