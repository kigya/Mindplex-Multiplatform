package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import coil3.compose.AsyncImage
import dev.carlsen.flagkit.FlagKit
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardBackground
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardCrown
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardUserPodiumRankText
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardUserPodiumScoreText
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_crown
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard_points
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal object UserScoreCardConstants {
    const val CARD_FRACTION = 0.43f
}

@Composable
internal fun UserScoreCard(
    state: LeaderboardContract.State.UserCardData,
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
    ) {
        Spacer(modifier = Modifier.height(LeaderboardTheme.dimension.dp16.value))
        if (isFirstRank) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                MindplexIcon(
                    resource = Res.drawable.ic_crown,
                    color = LeaderboardTheme.colorScheme.leaderboardCrown,
                )

                MindplexText(
                    modifier = Modifier.padding(top = LeaderboardTheme.dimension.dp8.value),
                    value = state.userRank,
                    color = LeaderboardTheme.colorScheme.leaderboardUserPodiumRankText,
                    typography = LeaderboardTheme.typography.leaderboardUserPodiumRankText,
                )
            }
        } else {
            MindplexText(
                value = state.userRank,
                color = LeaderboardTheme.colorScheme.leaderboardUserPodiumRankText,
                typography = LeaderboardTheme.typography.leaderboardUserPodiumRankText,
            )
        }

        Box(
            modifier = Modifier
                .width(LeaderboardTheme.dimension.dp48.value)
                .padding(vertical = LeaderboardTheme.dimension.dp8.value),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(LeaderboardTheme.dimension.dp48.value)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                model = state.avatarUrl,
                contentDescription = String.empty,
                error = painterResource(Res.drawable.ic_profile_fallback),
                fallback = painterResource(
                    resource = Res.drawable.ic_profile_fallback,
                ),
            )

            state.countryCode?.let { countryCode ->
                FlagKit.getFlag(countryCode)?.let { flagImageVector ->
                    Image(
                        imageVector = flagImageVector,
                        contentDescription = String.empty,
                        modifier = Modifier
                            .clip(RoundedCornerShape(LeaderboardTheme.dimension.dp4.value))
                            .align(Alignment.BottomEnd),
                    )
                }
            }
        }

        MindplexText(
            modifier = Modifier.padding(horizontal = LeaderboardTheme.dimension.dp8.value),
            value = state.userName,
            color = LeaderboardTheme.colorScheme.leaderboardUserPodiumRankText,
            typography = LeaderboardTheme.typography.leaderboardUserPodiumRankText,
            maxLines = 2,
        )

        MindplexText(
            modifier = Modifier.padding(vertical = LeaderboardTheme.dimension.dp2.value),
            value = stringResource(Res.string.leaderboard_points, state.userScore),
            color = LeaderboardTheme.colorScheme.leaderboardUserPodiumScoreText,
            typography = LeaderboardTheme.typography.leaderboardUserPodiumScoreText,
        )
    }
}
