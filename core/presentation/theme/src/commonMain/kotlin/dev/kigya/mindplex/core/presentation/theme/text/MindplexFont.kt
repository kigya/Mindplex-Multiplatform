@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import mindplex_multiplatform.core.presentation.theme.generated.resources.Res
import mindplex_multiplatform.core.presentation.theme.generated.resources.nunito_extrabold
import mindplex_multiplatform.core.presentation.theme.generated.resources.rubik_medium
import mindplex_multiplatform.core.presentation.theme.generated.resources.rubik_regular
import org.jetbrains.compose.resources.Font

data object MindplexFont {

    val rubik: MindplexDsToken<FontFamily>
        @Composable
        get() = MindplexDsToken(
            FontFamily(
                Font(
                    resource = Res.font.rubik_medium,
                    weight = MindplexFontWeight.medium.value,
                    style = FontStyle.Normal,
                ),
                Font(
                    resource = Res.font.rubik_regular,
                    weight = MindplexFontWeight.normal.value,
                    style = FontStyle.Normal,
                ),
            ),
        )

    val nunito: MindplexDsToken<FontFamily>
        @Composable
        get() = MindplexDsToken(
            FontFamily(
                Font(
                    resource = Res.font.nunito_extrabold,
                    weight = MindplexFontWeight.extraBold.value,
                    style = FontStyle.Normal,
                ),
            ),
        )
}
