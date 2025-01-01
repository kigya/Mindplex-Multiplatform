package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontWeight
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken

@Immutable
data object MindplexFontWeight {
    val medium: MindplexDsToken<FontWeight> = MindplexDsToken(FontWeight.Medium)
    val normal: MindplexDsToken<FontWeight> = MindplexDsToken(FontWeight.Normal)
    val extraBold: MindplexDsToken<FontWeight> = MindplexDsToken(FontWeight.ExtraBold)
}
