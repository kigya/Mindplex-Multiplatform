@file:Suppress("TopLevelPropertyNaming", "MagicNumber", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken

@Immutable
data class MindplexColor internal constructor(
    val iris100: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF4C4580)),
    val iris90: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF49408E)),
    val iris80: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF5D51B3)),
    val iris70: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF6A5AE0)),
    val iris60: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF7C6FD6)),
    val iris50: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF887AEA)),
    val iris40: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFA69CF6)),
    val iris30: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFC4BFED)),
    val iris20: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFE3E1FA)),
    val iris10: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFEFEEFC)),
    val white: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFFFFFFF)),
    val gunmetal100: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF263238)),
    val gunmetal80: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF707070)),
    val gunmetal60: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFC1BFD3)),
    val apple: MindplexDsToken<Color> = MindplexDsToken(Color(0xFF32A83E)),
    val mandy: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFEB5D60)),
    val crayola: MindplexDsToken<Color> = MindplexDsToken(Color(0xFFFF4B4F)),
    val transparent: MindplexDsToken<Color> = MindplexDsToken(Color(0x00000000)),
)
