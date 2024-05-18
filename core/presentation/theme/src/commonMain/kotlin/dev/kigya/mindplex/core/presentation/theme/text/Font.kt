@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.kigya.mindplex.core.presentation.resources.ResourceProvider
import org.jetbrains.compose.resources.Font

internal val Rubik
    @Composable
    get() = FontFamily(
        Font(
            resource = ResourceProvider.Font.RUBIK_MEDIUM,
            weight = FontWeight.Medium,
            style = FontStyle.Normal,
        ),
        Font(
            resource = ResourceProvider.Font.RUBIK_REGULAR,
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        ),
    )

internal val Nunito
    @Composable
    get() = FontFamily(
        Font(
            resource = ResourceProvider.Font.NUNITO_EXTRABOLD,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Normal,
        ),
    )
