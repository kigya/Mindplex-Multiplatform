package dev.kigya.mindplex.feature.profile.presentation.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextAnimation
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileIcons
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.userStatisticCategoryNameText
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.userStatisticScoreText
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.userStatisticsCardBackground
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.verticalDivider
import mindplex_multiplatform.feature.profile.presentation.generated.resources.Res
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_flag
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_star
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_world
import mindplex_multiplatform.feature.profile.presentation.generated.resources.local_rank
import mindplex_multiplatform.feature.profile.presentation.generated.resources.points
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_category_preview
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_score_preview
import mindplex_multiplatform.feature.profile.presentation.generated.resources.world_rank
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

private const val USER_CARD_STATISTIC_FRACTION = 0.3f

@Composable
internal fun UserStatisticsCard(
    state: ProfileContract.State.UserProfile,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(USER_CARD_STATISTIC_FRACTION)
            .shadow(
                ProfileTheme.dimension.dp16.value,
                RoundedCornerShape(ProfileTheme.dimension.dp24.value),
            )
            .clip(RoundedCornerShape(ProfileTheme.dimension.dp24.value))
            .background(ProfileTheme.colorScheme.userStatisticsCardBackground.value),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UserStatistic(
            image = Res.drawable.ic_star,
            name = stringResource(Res.string.points),
            score = state.userScore,
            isLoading = isLoading,
            showHash = false,
        )

        VerticalDivider(color = ProfileTheme.colorScheme.verticalDivider.value)

        UserStatistic(
            image = Res.drawable.ic_world,
            name = stringResource(Res.string.world_rank),
            score = state.userGlobalRank,
            isLoading = isLoading,
            showHash = true,
        )

        VerticalDivider(color = ProfileTheme.colorScheme.verticalDivider.value)

        UserStatistic(
            image = Res.drawable.ic_flag,
            name = stringResource(Res.string.local_rank),
            score = state.userLocalRank,
            isLoading = isLoading,
            showHash = true,
        )
    }
}

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
private fun UserStatistic(
    image: DrawableResource,
    name: String,
    score: String,
    isLoading: Boolean,
    showHash: Boolean,
) {
    Column(
        modifier = Modifier.width(ProfileTheme.dimension.dp80.value),
        verticalArrangement = Arrangement.spacedBy(ProfileTheme.dimension.dp12.value),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MindplexMeasurablePlaceholder(isLoading = isLoading) {
            MindplexIcon(
                resource = image,
                color = ProfileTheme.colorScheme.profileIcons,
            )
        }

        MindplexMeasurablePlaceholder(
            textToMeasure = stringResource(Res.string.profile_category_preview),
            textStyle = ProfileTheme.typography.userStatisticCategoryNameText,
            isLoading = isLoading,
        ) {
            MindplexText(
                value = name,
                color = ProfileTheme.colorScheme.userStatisticCategoryNameText,
                typography = ProfileTheme.typography.userStatisticCategoryNameText,
            )
        }

        MindplexMeasurablePlaceholder(
            textToMeasure = stringResource(Res.string.profile_score_preview),
            textStyle = ProfileTheme.typography.userStatisticScoreText,
            isLoading = isLoading,
        ) {
            MindplexText(
                value = score,
                color = ProfileTheme.colorScheme.userStatisticScoreText,
                typography = ProfileTheme.typography.userStatisticScoreText,
                animation = MindplexTextAnimation.MovingText,
                prefix = showHash,
            )
        }
    }
}
