@file:Suppress("TopLevelPropertyNaming", "MagicNumber", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Iris100 = Color(0xFF4C4580)
val Iris90 = Color(0xFF49408E)
val Iris80 = Color(0xFF5D51B3)
val Iris70 = Color(0xFF6A5AE0)
val Iris60 = Color(0xFF7C6FD6)
val Iris50 = Color(0xFF887AEA)
val Iris40 = Color(0xFFA69CF6)
val Iris30 = Color(0xFFC4BFED)
val Iris20 = Color(0xFFE3E1FA)
val Iris10 = Color(0xFFEFEEFC)

val White = Color(0xFFFFFFFF)

val Gunmetal100 = Color(0xFF263238)
val Gunmetal80 = Color(0xFF707070)
val Gunmetal60 = Color(0xFFC1BFD3)

val AmericanGreen = Color(0xFF32A83E)
val Crayola = Color(0xFFFF4B4F)

val ColorScheme.quaternary: Color
    @Composable
    get() = if (isSystemInDarkTheme().not()) Iris70 else Iris90
