package dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexColorScheme
import dev.kigya.mindplex.core.presentation.theme.color.MindplexDynamicColor
import dev.kigya.mindplex.core.presentation.theme.color.provides
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTextStyle
import dev.kigya.mindplex.core.presentation.theme.text.MindplexTypography
import dev.kigya.mindplex.core.presentation.theme.text.provides

internal object LeaderboardTheme : MindplexTheme() {

    val MindplexColorScheme.leaderboardBackground
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.iris10,
                dark = super.color.iris80,
            )

    val MindplexColorScheme.leaderboardNameText
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.gunmetal100,
                dark = super.color.white,
            )

    val MindplexColorScheme.branchesTint
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.iris80,
                dark = super.color.iris100,
            )

    val MindplexColorScheme.userPodiumPlaceText
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.gunmetal100,
                dark = super.color.white,
            )

    val MindplexColorScheme.userPodiumScoreText
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.gunmetal80,
                dark = super.color.gunmetal60,
            )

    val MindplexColorScheme.crown
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.yellow100,
                dark = super.color.iris80,
            )

    val MindplexColorScheme.firstPlaceCardColor
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.yellow50,
                dark = super.color.yellow50,
            )

    val MindplexColorScheme.secondPlaceCardColor
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.white,
                dark = super.color.white,
            )

    val MindplexColorScheme.thirdPlaceCardColor
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.red100,
                dark = super.color.red100,
            )

    val MindplexColorScheme.dividerLineUserPlaceColor
        @Composable
        get() =
            this provides MindplexDynamicColor(
                light = super.color.iris20,
                dark = super.color.gunmetal60,
            )

    // typography
    val MindplexTypography.leaderboardNameText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp20,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.userPodiumPlaceText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp14,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )

    val MindplexTypography.userPodiumScoreText
        @Composable
        get() = this provides MindplexTextStyle(
            fontSize = super.textSize.sp12,
            fontWeight = super.fontWeight.medium,
            fontFamily = super.font.rubik,
        )
}
