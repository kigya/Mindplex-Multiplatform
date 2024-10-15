@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
class MindplexTypography internal constructor()

internal val LocalTypography = staticCompositionLocalOf(::MindplexTypography)
