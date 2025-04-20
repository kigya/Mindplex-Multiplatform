package dev.kigya.mindplex.feature.leaderboard.presentation.block.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.leaderboard.presentation.block.UserScoreCardConstants
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardBackground
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardCrown
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardUserPodiumRankText
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardUserPodiumScoreText
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_crown
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard_points
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
internal fun ShimmerUserScoreCard(
    topColumnGradientColor: Color,
    isFirstRank: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(UserScoreCardConstants.CARD_FRACTION)
            .clip(shape = RoundedCornerShape(LeaderboardTheme.dimension.dp16.value))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        topColumnGradientColor,
                        LeaderboardTheme.colorScheme.leaderboardBackground.value,
                    ),
                ),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LeaderboardTheme.dimension.dp8.value),
    ) {
        Spacer(modifier = Modifier.height(LeaderboardTheme.dimension.dp16.value))
        if (isFirstRank) {
            Box(contentAlignment = Alignment.Center) {
                MindplexIcon(
                    resource = Res.drawable.ic_crown,
                    color = LeaderboardTheme.colorScheme.leaderboardCrown,
                )

                MindplexText(
                    value = String.empty,
                    color = LeaderboardTheme.colorScheme.leaderboardUserPodiumRankText,
                    typography = LeaderboardTheme.typography.leaderboardUserPodiumRankText,
                )
            }
        } else {
            MindplexText(
                value = String.empty,
                color = LeaderboardTheme.colorScheme.leaderboardUserPodiumRankText,
                typography = LeaderboardTheme.typography.leaderboardUserPodiumRankText,
            )
        }

        MindplexMeasurablePlaceholder(isLoading = true) {
            Box(modifier = Modifier.size(LeaderboardTheme.dimension.dp48.value))
        }

        MindplexMeasurablePlaceholder(
            isLoading = true,
            textToMeasure = stringResource(Res.string.leaderboard_points),
            textStyle = LeaderboardTheme.typography.leaderboardUserPodiumScoreText,
        ) {
            Box(modifier = Modifier.width(LeaderboardTheme.dimension.dp24.value))
        }
    }
}
