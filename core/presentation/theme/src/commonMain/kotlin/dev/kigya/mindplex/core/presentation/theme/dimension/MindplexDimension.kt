@file:Suppress("TopLevelPropertyNaming", "PropertyName")

package dev.kigya.mindplex.core.presentation.theme.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken

@Immutable
data object MindplexDimension {
    val dp0: MindplexDsToken<Dp> = MindplexDsToken(0.dp)
    val dp1: MindplexDsToken<Dp> = MindplexDsToken(1.dp)
    val dp2: MindplexDsToken<Dp> = MindplexDsToken(2.dp)
    val dp4: MindplexDsToken<Dp> = MindplexDsToken(4.dp)
    val dp8: MindplexDsToken<Dp> = MindplexDsToken(8.dp)
    val dp12: MindplexDsToken<Dp> = MindplexDsToken(12.dp)
    val dp16: MindplexDsToken<Dp> = MindplexDsToken(16.dp)
    val dp24: MindplexDsToken<Dp> = MindplexDsToken(24.dp)
    val dp36: MindplexDsToken<Dp> = MindplexDsToken(36.dp)
    val dp48: MindplexDsToken<Dp> = MindplexDsToken(48.dp)
    val dp64: MindplexDsToken<Dp> = MindplexDsToken(64.dp)
    val dp80: MindplexDsToken<Dp> = MindplexDsToken(80.dp)
    val dp90: MindplexDsToken<Dp> = MindplexDsToken(90.dp)
    val dp128: MindplexDsToken<Dp> = MindplexDsToken(128.dp)
}
