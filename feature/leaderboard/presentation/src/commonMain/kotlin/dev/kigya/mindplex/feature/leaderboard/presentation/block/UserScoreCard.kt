package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import coil3.compose.AsyncImage
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.crown
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardBackground
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.userPodiumPlaceText
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.userPodiumScoreText
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_crown
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard_points
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val MAX_LINER_USER_NAME = 2
private const val CARD_WIDTH = 0.43f

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
internal fun UserScoreCard(
    modifier: Modifier = Modifier,
    state: LeaderboardContract.State.UserCardData,
    topColumnGradientColor: Color,
    isFirstPlace: Boolean,
    leaderboardLoading: LeaderboardContract.State.LeaderboardScreenLoadingData,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(CARD_WIDTH)
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
        MindplexSpacer(modifier = modifier.height(LeaderboardTheme.dimension.dp8.value))
        if (isFirstPlace) {
            Box(contentAlignment = Alignment.Center) {
                MindplexIcon(
                    resource = Res.drawable.ic_crown,
                    color = LeaderboardTheme.colorScheme.crown,
                )

                MindplexText(
                    value = state.userPlace,
                    color = LeaderboardTheme.colorScheme.userPodiumPlaceText,
                    typography = LeaderboardTheme.typography.userPodiumPlaceText,
                )
            }
        } else {
            MindplexText(
                value = state.userPlace,
                color = LeaderboardTheme.colorScheme.userPodiumPlaceText,
                typography = LeaderboardTheme.typography.userPodiumPlaceText,
            )
        }

        MindplexMeasurablePlaceholder(isLoading = leaderboardLoading.isLeaderboardLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(LeaderboardTheme.dimension.dp48.value)
                    .clip(CircleShape),
                model = state.avatarUrl,
                contentDescription = String.empty,
                error = painterResource(Res.drawable.ic_profile_fallback),
                fallback = painterResource(
                    resource = Res.drawable.ic_profile_fallback,
                ),
            )
        }

        MindplexText(
            value = state.userName,
            color = LeaderboardTheme.colorScheme.userPodiumPlaceText,
            typography = LeaderboardTheme.typography.userPodiumPlaceText,
            maxLines = MAX_LINER_USER_NAME,
        )

        MindplexMeasurablePlaceholder(
            isLoading = leaderboardLoading.isLeaderboardLoading,
            textToMeasure = stringResource(Res.string.leaderboard_points),
            textStyle = LeaderboardTheme.typography.userPodiumScoreText,
        ) {
            MindplexText(
                value = stringResource(Res.string.leaderboard_points, state.userScore),
                color = LeaderboardTheme.colorScheme.userPodiumScoreText,
                typography = LeaderboardTheme.typography.userPodiumScoreText,
            )
        }
    }
}
