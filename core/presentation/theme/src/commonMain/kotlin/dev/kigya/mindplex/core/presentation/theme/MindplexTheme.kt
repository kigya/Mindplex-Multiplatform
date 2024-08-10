@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import dev.kigya.mindplex.core.presentation.theme.color.LocalColor
import dev.kigya.mindplex.core.presentation.theme.color.LocalColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColor
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.dimension.LocalDimension
import dev.kigya.mindplex.core.presentation.theme.dimension.MindplexDimension
import dev.kigya.mindplex.core.presentation.theme.shape.LocalShape
import dev.kigya.mindplex.core.presentation.theme.shape.MindplexShape
import dev.kigya.mindplex.core.presentation.theme.text.LocalFont
import dev.kigya.mindplex.core.presentation.theme.text.LocalTextSize
import dev.kigya.mindplex.core.presentation.theme.text.LocalTypography
import dev.kigya.mindplex.core.presentation.theme.text.MindplexFont
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextSize
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.core.presentation.theme.window.Window

@Composable
fun MindplexTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        values = arrayOf(
            LocalDimension provides MindplexDimension(),
            LocalTextSize provides MindplexTextSize(),
            LocalTypography provides MindplexTypography(),
            LocalWindow provides Window,
            LocalFont provides MindplexFont,
        ),
        content = content,
    )
}

/**
 * Mindplex Theming refers to the customization of app's design system to better reflect
 * product’s brand and unique style.
 *
 * Components such as [Button] and [Text] use values provided here when retrieving default values.
 *
 * All values may be set by providing this object with the [colorScheme][MindplexColorScheme],
 * [typography][MindplexTypography], [textSize][MindplexTextSize], [font][MindplexFont],
 * [dimension][MindplexDimension], and [shape][MindplexShape] attributes.
 * Use this to configure the overall theme of elements within this MindplexTheme.
 *
 * Any values that are not set will inherit the current value from the theme, falling back to the
 * defaults if there is no parent MindplexTheme. This allows using a MindplexTheme at the top of
 * the application, and then separate MindplexTheme(s) for different screens or parts of UI,
 * overriding only the parts of the theme definition that need to change.
 *
 * ### Example Usage:
 *
 * #### Typography:
 * ```kotlin
 * MindplexText(
 *     text = stringResource(Res.string.splash_title),
 *     style = MindplexTheme.typography.splashHeader,
 *     color = MindplexTheme.colorScheme.splashTitle,
 * )
 *
 * internal val MindplexTypography.splashHeader
 *     @Composable
 *     get() = TextStyle(
 *         fontSize = MindplexTheme.textSize.sp36,
 *         fontWeight = FontWeight.ExtraBold,
 *         fontFamily = MindplexTheme.font.nunito,
 *     )
 *
 * internal val MindplexTypography.loginTitle
 *     @Composable
 *     get() = TextStyle(
 *         fontSize = MindplexTheme.textSize.sp24,
 *         fontWeight = FontWeight.Medium,
 *         fontFamily = MindplexTheme.font.rubik,
 *     )
 * ```
 *
 * #### Dimension:
 * ```kotlin
 * @Composable
 * fun MindplexSpacer(
 *     modifier: Modifier = Modifier,
 *     orientation: MindplexSpacerOrientation,
 *     size: Dp = MindplexTheme.dimension.dp0,
 * ) = drawSpacer(modifier, orientation, size)
 * ```
 *
 * #### Color Scheme:
 * ```kotlin
 * MindplexIcon(
 *     drawableResource = Res.drawable.ic_mindplex,
 *     tintColor = MindplexTheme.colorScheme.loginMindplexIcon,
 * )
 *
 * internal val MindplexColorScheme.splashBackground
 *     @Composable
 *     get() = this provides MindplexDynamicColor(
 *         light = MindplexTheme.color.iris70,
 *         dark = MindplexTheme.color.iris80,
 *     )
 * ```
 *
 * #### Shape:
 * ```kotlin
 * Row(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(IntrinsicSize.Min)
 *         .clip(MindplexTheme.shape.rounding16)
 * )
 * ```
 *
 * @param typography A set of text styles to be used as this hierarchy's typography system
 * @param textSize A set of text sizes to be used as this hierarchy's size system
 * @param font A set of font styles to be used as this hierarchy's font system
 * @param dimension A set of dimensions (e.g., padding, margin) to be used as this hierarchy's dimension system
 * @param colorScheme A complete definition of the color theme for this hierarchy
 * @param color A complete set of colors to be used across the application
 * @param shape A set of shapes to be used as this hierarchy's shape system
 */
object MindplexTheme {
    val typography: MindplexTypography
        @Composable @ReadOnlyComposable
        get() = LocalTypography.current

    val textSize: MindplexTextSize
        @Composable @ReadOnlyComposable
        get() = LocalTextSize.current

    val font: MindplexFont
        @Composable @ReadOnlyComposable
        get() = LocalFont.current

    val dimension: MindplexDimension
        @Composable @ReadOnlyComposable
        get() = LocalDimension.current

    val colorScheme: MindplexColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalColorScheme.current

    val color: MindplexColor
        @Composable @ReadOnlyComposable
        get() = LocalColor.current

    val shape: MindplexShape
        @Composable @ReadOnlyComposable
        get() = LocalShape.current
}
