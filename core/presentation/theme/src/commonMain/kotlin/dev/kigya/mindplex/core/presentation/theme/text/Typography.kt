package dev.kigya.mindplex.core.presentation.theme.text

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

internal val Typography
    @Composable
    get() = Typography(
        displaySmall = TextStyle(
            fontSize = TextSize.large,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        headlineLarge = TextStyle(
            fontSize = TextSize.giant,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        headlineMedium = TextStyle(
            fontSize = TextSize.extraLarge,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        headlineSmall = TextStyle(
            fontSize = TextSize.medium,
            fontWeight = FontWeight.Normal,
            fontFamily = Rubik,
        ),
        titleLarge = TextStyle(
            fontSize = TextSize.medium,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        titleMedium = TextStyle(
            fontSize = TextSize.extraSmall,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        titleSmall = TextStyle(
            fontSize = TextSize.extraSmall,
            fontWeight = FontWeight.Normal,
            fontFamily = Rubik,
        ),
        bodyMedium = TextStyle(
            fontSize = TextSize.extraSmall,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        bodySmall = TextStyle(
            fontSize = TextSize.extraSmall,
            fontWeight = FontWeight.Normal,
            fontFamily = Rubik,
        ),
        labelLarge = TextStyle(
            fontSize = TextSize.extraLarge,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        labelMedium = TextStyle(
            fontSize = TextSize.medium,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
        labelSmall = TextStyle(
            fontSize = TextSize.small,
            fontWeight = FontWeight.Medium,
            fontFamily = Rubik,
        ),
    )
