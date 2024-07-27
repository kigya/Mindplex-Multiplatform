package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
fun measureText(text: String, style: TextStyle): Size {
    val textMeasurer = rememberTextMeasurer()
    val widthInPixels = textMeasurer.measure(text, style).size.width
    val heightInPixels = textMeasurer.measure(text, style).size.height
    return Size(
        width = widthInPixels.toFloat(),
        height = heightInPixels.toFloat(),
    )
}
