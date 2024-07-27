@file:Suppress("TopLevelPropertyNaming", "MagicNumber", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Color.Companion.Iris100: Color
    get() = Color(0xFF4C4580)
val Color.Companion.Iris90: Color
    get() = Color(0xFF49408E)
val Color.Companion.Iris80: Color
    get() = Color(0xFF5D51B3)
val Color.Companion.Iris70: Color
    get() = Color(0xFF6A5AE0)
val Color.Companion.Iris60: Color
    get() = Color(0xFF7C6FD6)
val Color.Companion.Iris50: Color
    get() = Color(0xFF887AEA)
val Color.Companion.Iris40: Color
    get() = Color(0xFFA69CF6)
val Color.Companion.Iris30: Color
    get() = Color(0xFFC4BFED)
val Color.Companion.Iris20: Color
    get() = Color(0xFFE3E1FA)
val Color.Companion.Iris10: Color
    get() = Color(0xFFEFEEFC)

val Color.Companion.Gunmetal100: Color
    get() = Color(0xFF263238)
val Color.Companion.Gunmetal80: Color
    get() = Color(0xFF707070)
val Color.Companion.Gunmetal60: Color
    get() = Color(0xFFC1BFD3)
val Color.Companion.AmericanGreen: Color
    get() = Color(0xFF32A83E)
val Color.Companion.Crayola: Color
    get() = Color(0xFFFF4B4F)

val ColorScheme.quaternary: Color
    @Composable
    get() = if (isSystemInDarkTheme().not()) Color.Iris70 else Color.Iris90

val ColorScheme.shimmerPrimary: Color
    @Composable
    get() = if (isSystemInDarkTheme().not()) Color.Iris30 else Color.Iris40
