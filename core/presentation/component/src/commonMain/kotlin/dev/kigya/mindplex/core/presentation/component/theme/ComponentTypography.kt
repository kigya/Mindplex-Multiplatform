package dev.kigya.mindplex.core.presentation.component.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

internal val MindplexTypography.componentTypewriterText
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp20,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.componentErrorStubTitle
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp16,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.componentErrorStubButton
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp16,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )
