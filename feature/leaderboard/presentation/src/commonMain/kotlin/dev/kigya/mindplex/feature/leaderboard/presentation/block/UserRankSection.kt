package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import dev.carlsen.flagkit.FlagKit
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardDividerLineUserRankColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardUserPodiumRankText
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardUserPodiumScoreText
import kotlinx.collections.immutable.ImmutableList
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard_points
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun UserRankSection(
    nonPodiumUsers: ImmutableList<LeaderboardContract.State.UserCardData>,
    bottomBarHeight: Dp,
    modifier: Modifier = Modifier,
) {
    if (nonPodiumUsers.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.padding(bottom = bottomBarHeight),
            overscrollEffect = null,
        ) {
            items(nonPodiumUsers.size) { index ->
                UserRankCard(
                    state = nonPodiumUsers[index],
                )
            }
        }
    }
}

@Composable
private fun UserRankCard(
    state: LeaderboardContract.State.UserCardData,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = LeaderboardTheme.dimension.dp8.value,
                horizontal = LeaderboardTheme.dimension.dp16.value,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LeaderboardTheme.dimension.dp8.value),
    ) {
        MindplexText(
            modifier = modifier.width(LeaderboardTheme.dimension.dp16.value),
            value = state.userRank,
            color = LeaderboardTheme.colorScheme.leaderboardUserPodiumRankText,
            typography = LeaderboardTheme.typography.leaderboardUserPodiumRankText,
        )

        Box(modifier = Modifier.width(LeaderboardTheme.dimension.dp48.value)) {
            AsyncImage(
                modifier = Modifier
                    .size(LeaderboardTheme.dimension.dp48.value)
                    .clip(CircleShape),
                model = state.avatarUrl,
                contentDescription = String.empty,
                error = painterResource(Res.drawable.ic_profile_fallback),
                fallback = painterResource(Res.drawable.ic_profile_fallback),
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
            modifier = modifier.weight(1f),
            value = state.userName,
            color = LeaderboardTheme.colorScheme.leaderboardUserPodiumRankText,
            typography = LeaderboardTheme.typography.leaderboardUserPodiumRankText,
            maxLines = 1,
            align = TextAlign.Start,
        )

        MindplexText(
            value = stringResource(Res.string.leaderboard_points, state.userScore),
            color = LeaderboardTheme.colorScheme.leaderboardUserPodiumScoreText,
            typography = LeaderboardTheme.typography.leaderboardUserPodiumScoreText,
        )
    }

    HorizontalDivider(
        color = LeaderboardTheme.colorScheme.leaderboardDividerLineUserRankColor.value,
    )
}
