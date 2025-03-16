@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken

@Immutable
data object MindplexTextSize {

    val sp12: MindplexDsToken<TextUnit>
        @Composable
        get() = MindplexDsToken(12.sp.nonScaledSp)
    val sp14: MindplexDsToken<TextUnit>
        @Composable
        get() = MindplexDsToken(14.sp.nonScaledSp)
    val sp16: MindplexDsToken<TextUnit>
        @Composable
        get() = MindplexDsToken(16.sp.nonScaledSp)
    val sp18: MindplexDsToken<TextUnit>
        @Composable
        get() = MindplexDsToken(18.sp.nonScaledSp)
    val sp20: MindplexDsToken<TextUnit>
        @Composable
        get() = MindplexDsToken(20.sp.nonScaledSp)
    val sp24: MindplexDsToken<TextUnit>
        @Composable
        get() = MindplexDsToken(24.sp.nonScaledSp)
    val sp36: MindplexDsToken<TextUnit>
        @Composable
        get() = MindplexDsToken(36.sp.nonScaledSp)
}

private val TextUnit.nonScaledSp: TextUnit
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp
