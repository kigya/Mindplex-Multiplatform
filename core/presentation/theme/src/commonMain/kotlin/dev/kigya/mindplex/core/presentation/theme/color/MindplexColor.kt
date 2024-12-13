@file:Suppress("TopLevelPropertyNaming", "MagicNumber", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class MindplexColor internal constructor(
    val iris100: Color = Color(0xFF4C4580),
    val iris90: Color = Color(0xFF49408E),
    val iris80: Color = Color(0xFF5D51B3),
    val iris70: Color = Color(0xFF6A5AE0),
    val iris60: Color = Color(0xFF7C6FD6),
    val iris50: Color = Color(0xFF887AEA),
    val iris40: Color = Color(0xFFA69CF6),
    val iris30: Color = Color(0xFFC4BFED),
    val iris20: Color = Color(0xFFE3E1FA),
    val iris10: Color = Color(0xFFEFEEFC),
    val white: Color = Color(0xFFFFFFFF),
    val gunmetal100: Color = Color(0xFF263238),
    val gunmetal80: Color = Color(0xFF707070),
    val gunmetal60: Color = Color(0xFFC1BFD3),
    val americanGreen: Color = Color(0xFF32A83E),
    val lightCarminePink: Color = Color(0xFFEB5D60),
    val crayola: Color = Color(0xFFFF4B4F),
    val transparent: Color = Color(0x00000000),
)

internal val LocalColor = staticCompositionLocalOf(::MindplexColor)
