package dev.kigya.mindplex.feature.profile.presentation.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileUserStatisticCategoryNameText
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileUserStatisticScoreText
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileUserStatisticsCardBackground
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileVerticalDivider
import mindplex_multiplatform.feature.profile.presentation.generated.resources.Res
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_flag
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_star
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_world
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_category_preview
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_hash
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_local_rank
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_points
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_score_preview
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_world_rank
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
            .background(ProfileTheme.colorScheme.profileUserStatisticsCardBackground.value),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UserStatistics(
            image = Res.drawable.ic_star,
            name = stringResource(Res.string.profile_points),
            score = state.userScore,
            isLoading = isLoading,
            shouldShowHash = false,
        )

        VerticalDivider(color = ProfileTheme.colorScheme.profileVerticalDivider.value)

        UserStatistics(
            image = Res.drawable.ic_world,
            name = stringResource(Res.string.profile_world_rank),
            score = state.userGlobalRank,
            isLoading = isLoading,
            shouldShowHash = true,
        )

        if (state.userCountry != null) {
            VerticalDivider(color = ProfileTheme.colorScheme.profileVerticalDivider.value)

            UserStatistics(
                image = Res.drawable.ic_flag,
                name = stringResource(Res.string.profile_local_rank),
                score = state.userLocalRank,
                isLoading = isLoading,
                shouldShowHash = true,
            )
        }
    }
}

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
private fun UserStatistics(
    image: DrawableResource,
    name: String,
    score: String,
    isLoading: Boolean,
    shouldShowHash: Boolean,
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
            textStyle = ProfileTheme.typography.profileUserStatisticCategoryNameText,
            isLoading = isLoading,
        ) {
            MindplexText(
                value = name,
                color = ProfileTheme.colorScheme.profileUserStatisticCategoryNameText,
                typography = ProfileTheme.typography.profileUserStatisticCategoryNameText,
            )
        }

        MindplexMeasurablePlaceholder(
            textToMeasure = stringResource(Res.string.profile_score_preview),
            textStyle = ProfileTheme.typography.profileUserStatisticScoreText,
            isLoading = isLoading,
        ) {
            if (shouldShowHash) {
                Row {
                    MindplexText(
                        value = stringResource(Res.string.profile_hash),
                        color = ProfileTheme.colorScheme.profileUserStatisticScoreText,
                        typography = ProfileTheme.typography.profileUserStatisticScoreText,
                    )
                    MindplexText(
                        modifier = Modifier.wrapContentWidth(),
                        value = score,
                        color = ProfileTheme.colorScheme.profileUserStatisticScoreText,
                        typography = ProfileTheme.typography.profileUserStatisticScoreText,
                        animation = MindplexTextAnimation.MovingText,
                    )
                }
            } else {
                MindplexText(
                    modifier = Modifier.wrapContentWidth(),
                    value = score,
                    color = ProfileTheme.colorScheme.profileUserStatisticScoreText,
                    typography = ProfileTheme.typography.profileUserStatisticScoreText,
                    animation = MindplexTextAnimation.MovingText,
                )
            }
        }
    }
}
