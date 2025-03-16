package dev.kigya.mindplex.feature.splash.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextStyle
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.text.provides

internal object SplashTheme : MindplexTheme() {

    // region ColorScheme
    val MindplexColorScheme.splashBackground
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.iris70,
            dark = super.color.iris80,
        )

    val MindplexColorScheme.splashTitle
        @Composable
        get() = this provides MindplexDynamicColor(
            light = super.color.white,
            dark = super.color.white,
        )
    // endregion ColorScheme

    // region Typography
    val MindplexTypography.splashHeader
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp36,
            fontWeight = super.fontWeight.extraBold,
            fontFamily = super.font.nunito,
        )
    // endregion Typography
}
