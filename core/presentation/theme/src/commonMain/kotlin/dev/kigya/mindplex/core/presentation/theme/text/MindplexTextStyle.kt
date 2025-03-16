package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken

@Immutable
data class MindplexTextStyle(
    val fontSize: MindplexDsToken<TextUnit>,
    val fontWeight: MindplexDsToken<FontWeight>,
    val fontFamily: MindplexDsToken<FontFamily>,
)

@Composable
infix fun MindplexTypography.provides(
    mindplexTextStyle: MindplexTextStyle,
): MindplexDsToken<TextStyle> = MindplexDsToken(
    TextStyle(
        fontSize = mindplexTextStyle.fontSize.value,
        fontWeight = mindplexTextStyle.fontWeight.value,
        fontFamily = mindplexTextStyle.fontFamily.value,
    ),
)
