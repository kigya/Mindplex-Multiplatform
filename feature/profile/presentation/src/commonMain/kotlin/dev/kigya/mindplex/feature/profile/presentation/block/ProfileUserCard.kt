package dev.kigya.mindplex.feature.profile.presentation.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import dev.carlsen.flagkit.FlagKit
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.userNameText
import mindplex_multiplatform.feature.profile.presentation.generated.resources.Res
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.profile.presentation.generated.resources.user_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
internal fun ProfileUserCard(
    modifier: Modifier = Modifier,
    state: ProfileContract.State.UserProfile,
    profileLoading: ProfileContract.State.ProfileScreenLoadingData,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ProfileTheme.dimension.dp64.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ProfileTheme.dimension.dp24.value),
    ) {
        MindplexMeasurablePlaceholder(isLoading = profileLoading.isProfileLoading) {
            Box(modifier = Modifier.width(ProfileTheme.dimension.dp132.value)) {
                AsyncImage(
                    modifier = Modifier
                        .size(ProfileTheme.dimension.dp128.value)
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    model = state.avatarUrl,
                    contentDescription = String.empty,
                    error = painterResource(Res.drawable.ic_profile_fallback),
                    fallback = painterResource(
                        resource = Res.drawable.ic_profile_fallback,
                    ),
                )

                state.userCountry?.let { countryCode ->
                    FlagKit.getFlag(countryCode)?.let { flagImageVector ->
                        Image(
                            imageVector = flagImageVector,
                            contentDescription = String.empty,
                            modifier = Modifier
                                .clip(RoundedCornerShape(ProfileTheme.dimension.dp4.value))
                                .align(Alignment.BottomEnd),
                        )
                    }
                }
            }
        }

        MindplexMeasurablePlaceholder(
            isLoading = profileLoading.isProfileLoading,
            textToMeasure = stringResource(Res.string.user_name),
            textStyle = ProfileTheme.typography.userNameText,
        ) {
            MindplexText(
                value = state.userName,
                color = ProfileTheme.colorScheme.userNameText,
                typography = ProfileTheme.typography.userNameText,
            )
        }

        UserStatisticsCard(state = state)
    }
}
