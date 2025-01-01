@file:Suppress("ObjectPropertyNaming")

package dev.kigya.mindplex.feature.login.presentation.ui.provider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.common.util.AdaptiveMetrics
import dev.kigya.mindplex.core.presentation.common.util.requiredCompositionLocalOf
import dev.kigya.mindplex.core.presentation.feature.provider.PlatformCompositionLocalProvider
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme

internal object LoginAdaptiveMetrics : AdaptiveMetrics() {

    private val loginButtonModifier
        @Composable
        get() = windowSizeClassWhen(
            compact = Modifier.fillMaxWidth(),
            medium = Modifier.wrapContentWidth(),
            expanded = Modifier.wrapContentWidth(),
        )
    val LocalLoginButtonModifier = requiredCompositionLocalOf<Modifier>()

    private val loginButtonsHorizontalPadding
        @Composable
        get() = windowSizeClassWhen(
            compact = LoginTheme.dimension.dp16,
            medium = LoginTheme.dimension.dp36,
            expanded = LoginTheme.dimension.dp48,
        )
    val LocalLoginButtonsHorizontalPadding = requiredCompositionLocalOf<MindplexDsToken<Dp>>()

    private val loginTextSpacing
        @Composable
        get() = windowSizeClassWhen(
            compact = LoginTheme.dimension.dp64,
            medium = LoginTheme.dimension.dp36,
            expanded = LoginTheme.dimension.dp36,
        )
    val LocalLoginTextSpacing = requiredCompositionLocalOf<MindplexDsToken<Dp>>()

    @Composable
    override fun provideValues(): Array<ProvidedValue<*>> = arrayOf(
        LocalLoginButtonModifier provides loginButtonModifier,
        LocalLoginButtonsHorizontalPadding provides loginButtonsHorizontalPadding,
        LocalLoginTextSpacing provides loginTextSpacing,
    )
}

@Composable
internal fun LoginCompositionLocalProvider(content: @Composable () -> Unit) =
    PlatformCompositionLocalProvider(
        metrics = LoginAdaptiveMetrics,
        content = content,
    )
