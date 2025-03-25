package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.userPodiumPlaceText
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.userPodiumScoreText
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.ic_profile_fallback
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun UserPlaceSection(
    modifier: Modifier = Modifier,
    state: LeaderboardContract.State.UserCardData,
    event: (LeaderboardContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(state.place.size) {
            UserPlaceCard(
                state = state,
                event = event,
            )
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
    ) {
        MindplexText(
            value = state.userPlace,
            color = LeaderboardTheme.colorScheme.userPodiumPlaceText,
            typography = LeaderboardTheme.typography.userPodiumPlaceText,
        )

        MindplexMeasurablePlaceholder(isLoading = state.isProfilePictureLoading && state.isProfileNameLoading) {
            AsyncImage(
                modifier = Modifier
                    .padding(horizontal = LeaderboardTheme.dimension.dp4.value)
                    .size(LeaderboardTheme.dimension.dp48.value)
                    .clip(CircleShape),
                model = state.avatarUrl,
                contentDescription = String.empty,
                error = painterResource(Res.drawable.ic_profile_fallback),
                fallback = painterResource(
                    resource = Res.drawable.ic_profile_fallback,
                ).takeIf { state.isProfileNameLoading.not() },
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
            value = state.userScore,
            color = LeaderboardTheme.colorScheme.userPodiumScoreText,
            typography = LeaderboardTheme.typography.userPodiumScoreText,
        )
    }

    HorizontalDivider(
        modifier = modifier.width(LeaderboardTheme.dimension.dp1.value),
        color = LeaderboardTheme.colorScheme.userPodiumPlaceText.value,
    )
}
