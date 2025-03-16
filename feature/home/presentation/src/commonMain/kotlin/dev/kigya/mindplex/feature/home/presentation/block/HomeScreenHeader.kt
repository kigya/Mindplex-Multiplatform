package dev.kigya.mindplex.feature.home.presentation.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextAnimation
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeProfileNameText
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeWelcomeBackText
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_user_name_preview
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_welcome_back
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_profile_fallback
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
internal fun HomeScreenHeader(
    state: HomeContract.State.HeaderData,
    modifier: Modifier = Modifier,
    event: (HomeContract.Event) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            MindplexMeasurablePlaceholder(isLoading = state.isProfileNameLoading) {
                MindplexText(
                    value = stringResource(Res.string.home_welcome_back),
                    color = HomeTheme.colorScheme.homeWelcomeBackText,
                    typography = HomeTheme.typography.homeWelcomeBackText,
                )
            }

            MindplexSpacer(size = HomeTheme.dimension.dp8)

            MindplexMeasurablePlaceholder(
                isLoading = state.isProfileNameLoading,
                textToMeasure = stringResource(Res.string.home_user_name_preview),
                textStyle = HomeTheme.typography.homeProfileNameText,
            ) {
                MindplexText(
                    value = state.userName,
                    color = HomeTheme.colorScheme.homeProfileNameText,
                    typography = HomeTheme.typography.homeProfileNameText,
                    animation = MindplexTextAnimation.Typewriter,
                )
            }
        }

        MindplexMeasurablePlaceholder(isLoading = state.isProfilePictureLoading && state.isProfileNameLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(HomeTheme.dimension.dp48.value)
                    .clip(CircleShape),
                model = state.avatarUrl,
                contentDescription = null,
                error = painterResource(Res.drawable.ic_profile_fallback),
                fallback = painterResource(
                    resource = Res.drawable.ic_profile_fallback,
                ).takeIf { state.isProfileNameLoading.not() },
                onError = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
                onSuccess = { event(HomeContract.Event.OnProfilePictureLoaded) },
            )
        }
    }
}
