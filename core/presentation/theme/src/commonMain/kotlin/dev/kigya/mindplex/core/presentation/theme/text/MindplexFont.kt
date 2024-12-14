@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import mindplex_multiplatform.core.presentation.theme.generated.resources.Res
import mindplex_multiplatform.core.presentation.theme.generated.resources.nunito_extrabold
import mindplex_multiplatform.core.presentation.theme.generated.resources.rubik_medium
import mindplex_multiplatform.core.presentation.theme.generated.resources.rubik_regular
import org.jetbrains.compose.resources.Font

data object MindplexFont {
    val rubik: FontFamily
        @Composable
        get() = FontFamily(
            Font(
                resource = Res.font.rubik_medium,
                weight = MindplexFontWeight.medium,
                style = FontStyle.Normal,
            ),
            Font(
                resource = Res.font.rubik_regular,
                weight = MindplexFontWeight.normal,
                style = FontStyle.Normal,
            ),
        )

    val nunito
        @Composable
        get() = FontFamily(
            Font(
                resource = Res.font.nunito_extrabold,
                weight = MindplexFontWeight.extraBold,
                style = FontStyle.Normal,
            ),
        )
}
