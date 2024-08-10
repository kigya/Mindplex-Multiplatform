package dev.kigya.mindplex.feature.splash.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

internal val MindplexTypography.splashHeader
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp36,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = MindplexTheme.font.nunito,
    )
