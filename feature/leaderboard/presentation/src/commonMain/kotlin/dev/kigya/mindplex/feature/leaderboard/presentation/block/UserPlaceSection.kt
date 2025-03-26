package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.dividerLineUserPlaceColor
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.userPodiumPlaceText
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.userPodiumScoreText
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard_points
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val SKIP_PODIUM_USERS = 3
private const val MAX_USERS_PLACE = 7

@Composable
internal fun UserPlaceSection(
    modifier: Modifier = Modifier,
    userPlaces: List<LeaderboardContract.State.UserCardData>,
    event: (LeaderboardContract.Event) -> Unit,
) {
    val filteredUsers = userPlaces
        .drop(SKIP_PODIUM_USERS)
        .take(MAX_USERS_PLACE)

    if (filteredUsers.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
        ) {
            items(filteredUsers.size) { index ->
                UserPlaceCard(
                    state = filteredUsers[index],
                    event = event,
                )
            }
        }
    }
}

@Composable
private fun UserPlaceCard(
    modifier: Modifier = Modifier,
    state: LeaderboardContract.State.UserCardData,
    event: (LeaderboardContract.Event) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = LeaderboardTheme.dimension.dp4.value,
                horizontal = LeaderboardTheme.dimension.dp8.value,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LeaderboardTheme.dimension.dp8.value),
    ) {
        MindplexText(
            value = state.userPlace,
            color = LeaderboardTheme.colorScheme.userPodiumPlaceText,
            typography = LeaderboardTheme.typography.userPodiumPlaceText,
        )

        MindplexMeasurablePlaceholder(isLoading = state.isProfilePictureLoading && state.isProfileNameLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(LeaderboardTheme.dimension.dp48.value)
                    .clip(CircleShape),
                model = state.avatarUrl,
                contentDescription = String.empty,
                error = painterResource(Res.drawable.ic_profile_fallback),
                fallback = painterResource(Res.drawable.ic_profile_fallback)
                    .takeIf { !state.isProfileNameLoading },
                onError = { event(LeaderboardContract.Event.OnProfilePictureErrorReceived) },
                onSuccess = { event(LeaderboardContract.Event.OnProfilePictureLoaded) },
            )
        }

        MindplexText(
            value = state.userName,
            color = LeaderboardTheme.colorScheme.userPodiumPlaceText,
            typography = LeaderboardTheme.typography.userPodiumPlaceText,
        )

        MindplexSpacer(modifier = modifier.weight(1f))

        MindplexText(
            value = stringResource(Res.string.leaderboard_points, state.userScore),
            color = LeaderboardTheme.colorScheme.userPodiumScoreText,
            typography = LeaderboardTheme.typography.userPodiumScoreText,
        )
    }

    HorizontalDivider(
        color = LeaderboardTheme.colorScheme.dividerLineUserPlaceColor.value,
    )
}
