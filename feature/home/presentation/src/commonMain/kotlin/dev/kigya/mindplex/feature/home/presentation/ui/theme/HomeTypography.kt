package dev.kigya.mindplex.feature.home.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography

internal val MindplexTypography.homeWelcomeBackText
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp12,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.homeProfileNameText
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp20,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.homeFactsPagerTitle
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp12,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.homeFactsPagerDescription
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp12,
        fontWeight = FontWeight.Normal,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.homeModesCardTitle
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp16,
        fontWeight = FontWeight.Medium,
        fontFamily = MindplexTheme.font.rubik,
    )

internal val MindplexTypography.homeModesCardDescription
    @Composable
    get() = TextStyle(
        fontSize = MindplexTheme.textSize.sp12,
        fontWeight = FontWeight.Normal,
        fontFamily = MindplexTheme.font.rubik,
    )
