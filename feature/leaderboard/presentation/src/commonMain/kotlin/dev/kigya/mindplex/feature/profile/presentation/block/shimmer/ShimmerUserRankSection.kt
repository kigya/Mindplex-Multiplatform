package dev.kigya.mindplex.feature.profile.presentation.block.shimmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.LeaderboardTheme.leaderboardDividerLineUserRankColor
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.LeaderboardTheme.leaderboardUserPodiumScoreText
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard_points
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard_user_name_preview
import org.jetbrains.compose.resources.stringResource

private const val NON_PODIUM_USER_COUNT = 7

@Composable
internal fun ShimmerUserRankSection(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(bottom = LeaderboardTheme.dimension.dp80.value),
        overscrollEffect = null,
    ) {
        items(NON_PODIUM_USER_COUNT) { _ ->
            ShimmerUserRankCard()
        }
    }
}

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
private fun ShimmerUserRankCard(modifier: Modifier = Modifier) {
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
        Box(
            modifier = modifier
                .width(LeaderboardTheme.dimension.dp16.value)
                .clip(RoundedCornerShape(LeaderboardTheme.dimension.dp16.value)),
        )

        MindplexMeasurablePlaceholder(isLoading = true) {
            Box(
                modifier = Modifier
                    .size(LeaderboardTheme.dimension.dp48.value)
                    .clip(RoundedCornerShape(LeaderboardTheme.dimension.dp16.value)),
            )
        }

        MindplexMeasurablePlaceholder(
            isLoading = true,
            textToMeasure = stringResource(Res.string.leaderboard_user_name_preview),
            textStyle = LeaderboardTheme.typography.leaderboardUserPodiumScoreText,
        ) {
            Box(
                modifier = modifier
                    .width(LeaderboardTheme.dimension.dp36.value)
                    .clip(RoundedCornerShape(LeaderboardTheme.dimension.dp16.value)),
            )
        }

        MindplexSpacer(modifier = modifier.weight(1f))

        MindplexMeasurablePlaceholder(
            isLoading = true,
            textToMeasure = stringResource(Res.string.leaderboard_points),
            textStyle = LeaderboardTheme.typography.leaderboardUserPodiumScoreText,
        ) {
            Box(
                modifier = modifier
                    .width(LeaderboardTheme.dimension.dp16.value)
                    .clip(RoundedCornerShape(LeaderboardTheme.dimension.dp16.value)),
            )
        }
    }

    HorizontalDivider(
        color = LeaderboardTheme.colorScheme.leaderboardDividerLineUserRankColor.value,
    )
}
