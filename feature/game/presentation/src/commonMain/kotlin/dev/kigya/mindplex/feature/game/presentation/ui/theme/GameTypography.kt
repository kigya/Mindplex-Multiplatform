package dev.kigya.mindplex.feature.game.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

internal val MindplexTypography.gameTitle
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp16,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.gameScoreLabel
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp16,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.gameTimerCounter
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp18,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.gameQuestion
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp20,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.gameButton
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp16,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )
